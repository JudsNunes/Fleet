# Proposta: Funcionalidades Avançadas de Frota Veicular

**ID**: JFE-FEAT-014 (proposta)
**Status**: Draft
**Autor**: Lucas Nunes
**Data**: 2026-06-22

---

## Contexto

O MVP atual cobre CRUD básico de veículos e manutenções. O CORE precisa evoluir para suportar as nuances operacionais reais de uma frota: alertas automáticos, validações inteligentes, conformidade fiscal e rastreabilidade completa de custos.

Esta proposta agrupa melhorias em 6 áreas que impactam diretamente o domínio do CORE e devem ser consumidas pelo BFF.

---

## 1. Alerta de Manutenção Preventiva

**O quê**: Disparar alertas automáticos ao atingir 90% da quilometragem recomendada ou data limite para revisão.

**Impacto no CORE**:
- Nova tabela `maintenance_alert` ou campo `alertThreshold` na entity
- Job `@Scheduled` que cruza `VehicleEntity.maintenanceIntervalKm` com `MaintenanceEntity.nextMileage`
- Alerta dispara quando `currentMileage >= nextMileage * 0.9`
- Para data: alerta quando `LocalDate.now()` atinge 90% do intervalo entre `maintenanceDate` e `nextMaintenanceDate`
- Evento `maintenance.alert` publicado via RabbitMQ para notification-service

**Não-objetivos**: Interface de configuração de intervalos (futuro), notificações push (notification-service)

---

## 2. Ficha Técnica Completa do Veículo

**O quê**: Campos obrigatórios adicionais na entidade Vehicle.

**Campos a adicionar em `VehicleEntity`**:

| Campo | Tipo | Obrigatório | Descrição |
|-------|------|-------------|-----------|
| `chassis` | String(17) | Sim | Número do chassi (VIN) |
| `renavam` | String(11) | Sim | Registro Nacional de Veículos Automotores |
| `fuelType` | Enum | Sim | GASOLINE, DIESEL, ETHANOL, FLEX, ELECTRIC, HYBRID |
| `cargoCapacityKg` | Double | Não | Capacidade de carga (kg) |
| `passengerCapacity` | Integer | Não | Capacidade de passageiros |
| `engineType` | String(50) | Não | Tipo do motor (ex: 1.0, 1.8, 2.0, EV) |

**Constraints**: `chassis` UNIQUE, `renavam` UNIQUE. Migration V2 necessária.

---

## 3. Validação de Combustível e Consumo

### 3.1 Validação de Combustível

**O quê**: Ao registrar abastecimento (type=FUEL na maintenance), cruzar o combustível da nota fiscal com o tipo aceito pelo motor.

**Regra**: Se `fuelType` do veículo é GASOLINA, impedir registro de DIESEL. Se FLEX, aceitar GASOLINA/ETANOL.

**Implementação**:
- Enum `FuelType`: GASOLINE, DIESEL, ETHANOL, FLEX, ELECTRIC, HYBRID
- Campo `invoiceFuelType` no registro de manutenção FUEL (ou nova entity `FuelRecord`)
- Validação no `MaintenanceService.create()` — lança `BusinessException` se incompatível

### 3.2 Consumo Médio Anômalo

**O quê**: Se consumo registrado estiver >20% acima da média histórica, sinalizar para verificação.

**Implementação**:
- Campo `litersFilled` e `distanceTraveled` nos registros FUEL
- Cálculo: `consumption = litersFilled / distanceTraveled` (km/L)
- Média histórica: média dos últimos 10 registros FUEL do veículo
- Se `currentConsumption < averageConsumption * 0.8` (gastou mais km/L = menos eficiente), flag `anomalousConsumption = true`
- Evento `maintenance.consumption_anomaly` publicado

---

## 4. Monitoramento de Rota

**O quê**: Registrar desvio de rota quando veículo percorre >15% do trajeto planejado sem justificativa.

**Implementação**:
- Nova entity `TripRoute` (ou extensão de `Trip` futuro): `plannedDistanceKm`, `actualDistanceKm`, `deviationPercent`, `justification`
- Cálculo: `deviationPercent = (actualDistance - plannedDistance) / plannedDistance * 100`
- Se `deviationPercent > 15` e `justification` é null/vazio → flag `routeDeviation = true`
- Endpoint: `PATCH /api/v1/trips/{id}/deviation` para registrar justificativa
- Evento `trip.route_deviation` para notification-service

**Nota**: Trip management ainda não existe no MVP atual. Esta feature depende de JFE-FEAT-007 (Gestão de Viagens).

---

## 5. Ordem de Serviço e Garantia

### 5.1 Ordem de Serviço (OS)

**O quê**: Nenhum gasto com oficina pode ser processado sem OS vinculada e aprovada.

**Implementação**:
- Nova entity `ServiceOrder`: id, vehicle, description, status (PENDING/APPROVED/REJECTED/COMPLETED), approvedBy, approvedAt, createdAt
- `MaintenanceEntity` ganha campo `serviceOrderId` (nullable) — obrigatório quando `type = MAINTENANCE`
- Validação: se `type == MAINTENANCE` e `serviceOrderId == null` → rejeitar
- Validação: se OS status != APPROVED → rejeitar

### 5.2 Garantia

**O quê**: Alertar se peça/serviço ainda está em garantia antes de autorizar novo pagamento.

**Implementação**:
- Campo `warrantyExpiryDate` na entity `ServiceOrder` ou nova entity `Warranty`
- Antes de criar Maintenance com `type = MAINTENANCE`, verificar se existe OS com `warrantyExpiryDate > now` para o mesmo veículo/tipo de serviço
- Se em garantia → alerta ao gestor (não impede, mas sinaliza)

---

## 6. Compliance e Custos

### 6.1 Rateio de Custos

**O quê**: Todo custo vinculado a centro de custo ou projeto.

**Implementação**:
- Campo `costCenterId` (String, nullable) em `MaintenanceEntity`
- Campo `projectId` (String, nullable) em `MaintenanceEntity`
- Pelo menos um deve ser preenchido (validação no service)
- Para FUEL, TOLL, PARKING: obrigatório
- Para MAINTENANCE: obrigatório (via OS)

### 6.2 Multas

**O quê**: Vínculo de multa ao CPF do condutor responsável.

**Implementação**:
- Nova entity `Fine`: id, vehicle, driverCpf, description, amount, infractionDate, points, status (PENDING/PAID/DISPUTED), costCenterId, createdAt
- Endpoint: `POST /api/v1/fines`, `GET /api/v1/fines?driverCpf=xxx`, `GET /api/v1/fines?vehicleId=xxx`
- Validação: `driverCpf` no formato XXX.XXX.XXX-XX

### 6.3 Auditoria

**O quê**: Log de toda alteração em regras de negócio críticas.

**Implementação**:
- Nova entity `AuditLog`: id, entityType, entityId, action (CREATE/UPDATE/DELETE/STATUS_CHANGE), fieldName, oldValue, newValue, userId, timestamp, ipAddress
- Aspecto Spring (`@Aspect`) ou `@EntityListener` que intercepta mudanças em campos críticos
- Campos críticos: `VehicleEntity.status`, `VehicleEntity.deletedAt`, `MaintenanceEntity.maintenanceStatus`, exclusões
- Endpoint: `GET /api/v1/audit-logs?entityType=VEHICLE&entityId=xxx`
- userId propagado via header `X-User-Id` (BFF preenche)

---

## Não-objetivos

- Interface de configuração de intervalos de manutenção (futuro)
- Notificações push/mobile (notification-service)
- Integração com GPS/telemetria (Futuro - W no roadmap)
- Multi-tenancy
- Export de relatórios (report-service)

---

## Priorização Sugerida

| Prioridade | Feature | Justificativa |
|------------|---------|---------------|
| **M** | Ficha Técnica (§2) | Dados obrigatórios para compliance legal |
| **M** | OS + Garantia (§5) | Controle financeiro essencial |
| **M** | Auditoria (§6.3) | Rastreabilidade obrigatória |
| **M** | Rateio de Custos (§6.1) | Gestão financeira básica |
| **S** | Alerta Manutenção (§1) | Valor operacional alto |
| **S** | Validação Combustível (§3.1) | Prevenção de erros |
| **S** | Consumo Médio (§3.2) | Inteligência operacional |
| **S** | Multas (§6.2) | Compliance com motoristas |
| **C** | Monitoramento Rota (§4) | Depende de trip-service |
| **C** | Consumo Médio avançado (§3.2) | Pode ser fase 2 |

---

## Estimativa de Esforço

| Área | Tasks | Estimativa |
|------|-------|------------|
| Ficha Técnica | Entity + DTO + Migration + OpenAPI + Tests | ~8h |
| Alerta Manutenção | Job + Event + Tests | ~6h |
| Validação Combustível | Validação + Tests | ~4h |
| Consumo Médio | Cálculo + Anomaly Detection + Tests | ~6h |
| OS + Garantia | Entity + Validação + Workflow + Tests | ~12h |
| Rateio de Custos | Campo + Validação + Tests | ~4h |
| Multas | Entity + CRUD + Tests | ~8h |
| Auditoria | Entity + Aspect + Endpoint + Tests | ~10h |
| **Total** | | **~58h (~7.5 dias)** |

---

## Impacto no BFF

| Feature | Impacto BFF |
|---------|-------------|
| Ficha Técnica | Formulário de veículo expandido |
| Alerta Manutenção | Dashboard: lista de alertas ativos |
| Validação Combustível | Toast de erro no form de abastecimento |
| Consumo Médio | Flag visual no histórico de abastecimentos |
| OS + Garantia | Tela de OS com aprovação, badge de garantia |
| Rateio de Custos | Select de centro de custo no form de despesa |
| Multas | CRUD de multas com select de motorista |
| Auditoria | Tela de auditoria com filtros |
