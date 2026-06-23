# Análise de Regras de Negócio — Core Fleet Management

## 1. Entidades de Domínio — O que Existe vs. O que Falta

### 1.1 Vehicle (Veículo)

| Regra de Negócio | Status | Observação |
|------------------|--------|------------|
| CRUD completo | ✅ Existe | Create, Read, Update, Soft-Delete |
| UUID como ID | ❌ Falta | Atualmente Long, precisa migrar |
| Status enum | ✅ Existe | ACTIVE, INACTIVE, MAINTENANCE, DECOMMISSIONED |
| Soft-delete | ✅ Existe | deleted_at timestamp |
| Audit trail | ✅ Existe | created_at, updated_at via @EnableJpaAuditing |
| Validação de placa | ⚠️ Parcial | Regex `[A-Z]{3}-\d{4}` (formato antigo) |
| Formato Mercosul | ❌ Falta | Placas Mercosul: `ABC1D23` (sem hífen) |
| Quilometragem | ✅ Existe | Campo mileage |
| Busca por placa | ⚠️ Parcial | findByPlate existe, mas sem filtro por status |
| Filtros combinados | ❌ Falta | Filtro por plate + brand + status simultâneos |
| Paginação | ✅ Existe | findAllPaged |
| Ordenação | ⚠️ Bug | Usa "id" em vez de "plate" |
| Validação year | ✅ Existe | CHECK constraint no DB |
| Validação mileage | ✅ Existe | CHECK constraint >= 0 |

**Regras Faltantes para Vehicle:**
1. Validação de placa Mercosul (formato novo)
2. Filtros combinados (plate, brand, status)
3. Endpoints de busca avançada
4. Validação de placa duplicada (existe no DB mas não tratado como 409)
5. Inativação via PATCH /status (não apenas soft-delete)

---

### 1.2 Maintenance (Manutenção)

| Regra de Negócio | Status | Observação |
|------------------|--------|------------|
| CRUD completo | ✅ Existe | Mas com bugs no mapper |
| UUID como ID | ❌ Falta | Precisa migrar |
| Vinculação com veículo | ✅ Existe | FK vehicle_id |
| Validação vehicleId | ❌ Falta | Não valida se veículo existe antes de salvar |
| Tipos de manutenção | ⚠️ Parcial | Enum typeCost: MANUTENTION, FUEL, TOLL, PARKING, OTHER |
| Nomenclatura | ⚠️ Bug | "Manutention" em vez de "Maintenance" |
| Status de conclusão | ✅ Existe | PENDING, IN_PROGRESS, COMPLETED |
| Status OVERDUE | ❌ Falta | Não detecta automaticamente manutenção vencida |
| Custo | ✅ Existe | Campo cost com validação > 0 |
| Quilometragem atual | ✅ Existe | Campo mileage |
| Próxima manutenção | ✅ Existe | Campo nextMileage |
| Data da manutenção | ✅ Existe | Campo manutentionDate |
| Busca por veículo | ✅ Existe | findByVehicleId |
| Busca por status | ✅ Existe | findByDone |
| Busca por período | ✅ Existe | findByVehicleIdAndDateRange |
| Alerta de vencimento | ❌ Falta | Job @Scheduled para detectar OVERDUE |
| Evento de domínio | ❌ Falta | maintenance.scheduled/completed/overdue |

**Regras Faltantes para Maintenance:**
1. Renomear Manutention → Maintenance
2. Validar vehicleId via chamada ao vehicle-service
3. Detectar automaticamente OVERDUE (mileage > nextMileage)
4. Job @Scheduled para verificação diária
5. Publicar eventos de domínio
6. Endpoints de busca por período

---

### 1.3 Driver (Motorista) — NÃO EXISTE

| Regra de Negócio | Status | Observação |
|------------------|--------|------------|
| Entidade | ❌ Não existe | Precisa criar |
| UUID como ID | ❌ Não existe | Criar com UUID |
| Nome completo | ❌ | Campo obrigatório |
| CPF | ❌ | Campo único, validação de formato |
| CNH (número) | ❌ | Campo único |
| Categoria CNH | ❌ | A, B, C, D, E |
| Validade CNH | ❌ | Data de vencimento |
| Status CNH | ⚠️ Existe enum | DriverLicenseStatus: ACTIVE, INACTIVE |
| Status CNH correto | ❌ Falta | Deveria ser: ACTIVE, EXPIRED, SUSPENDED |
| Telefone | ❌ | Para contato |
| Email | ❌ | Para notificações |
| Validação CPF | ❌ | Algoritmo de validação |
| Alerta vencimento CNH | ❌ | Job @Scheduled 30/15/7 dias |

**Regras para Driver:**
1. CRUD completo com validações
2. Validação de CPF (algoritmo completo)
3. Validação de CNH (formato + validade)
4. Status dinâmico (EXPIRED quando data passa)
5. Alertas de vencimento próximo
6. Busca por nome, CPF, status

---

### 1.4 VehicleAssignment (Vinculação Motorista-Veículo) — NÃO EXISTE

| Regra de Negócio | Status | Observação |
|------------------|--------|------------|
| Entidade | ❌ Não existe | Precisa criar |
| UUID como ID | ❌ | Criar com UUID |
| driverId | ❌ | Referência ao motorista |
| vehicleId | ❌ | Referência ao veículo |
| Regra: 1 motorista/veículo | ❌ | Apenas 1 assignment ativo por veículo |
| Regra: motorista ativo | ❌ | Só vincula motorista com CNH ACTIVE |
| Regra: veículo ativo | ❌ | Só vincula veículo ACTIVE |
| Data início | ❌ | Quando foi vinculado |
| Data fim | ❌ | Quando foi desvinculado |
| Status | ❌ | ACTIVE, INACTIVE |
| Eventos | ❌ | driver.assigned, driver.unassigned |

**Regras para Assignment:**
1. Criar/desativar vinculação
2. Validar regra de 1 ativo por veículo
3. Validar status do motorista e veículo
4. Consultar "motorista atual do veículo" e "veículo atual do motorista"
5. Publicar eventos

---

### 1.5 Trip (Viagem) — NÃO EXISTE

| Regra de Negócio | Status | Observação |
|------------------|--------|------------|
| Entidade | ❌ Não existe | Precisa criar |
| UUID como ID | ❌ | Criar com UUID |
| vehicleId | ❌ | Veículo utilizado |
| driverId | ❌ | Motorista que realizou |
| Origem | ❌ | Local de partida |
| Destino | ❌ | Local de chegada |
| Km inicial | ❌ | Quilometragem no início |
| Km final | ❌ | Quilometragem no fim |
| Distância calculada | ❌ | endKm - startKm |
| Data início | ❌ | Timestamp |
| Data fim | ❌ | Timestamp |
| Status | ❌ | IN_PROGRESS, COMPLETED, CANCELLED |
| Validação: sem assignment | ❌ | Não pode iniciar viagem sem veículo atribuído |
| Validação: km final > inicial | ❌ | Regra de negócio |
| Validação: só motorista dono | ❌ | RBAC: DRIVER só opera próprias viagens |
| Atualizar mileage vehicle | ❌ | Ao completar, atualizar mileage do veículo |
| Eventos | ❌ | trip.started, trip.completed |

**Regras para Trip:**
1. Iniciar viagem (validar assignment ativo)
2. Finalizar viagem (calcular distância, atualizar mileage)
3. Cancelar viagem
4. RBAC: motorista só vê/edita suas viagens
5. Histórico com filtros (veículo, motorista, período)
6. Agregação de km total

---

### 1.6 Notification (Notificação) — NÃO EXISTE

| Regra de Negócio | Status | Observação |
|------------------|--------|------------|
| Entidade | ❌ Não existe | Precisa criar |
| Consumer de eventos | ❌ | Consome maintenance.*, driver.*, trip.* |
| Persistência | ❌ | Tabela notifications |
| Types | ❌ | MAINTENANCE_OVERDUE, LICENSE_EXPIRING, TRIP_COMPLETED |
| Marcar como lida | ❌ | readAt timestamp |
| RBAC | ❌ | Cada usuário só vê suas notificações |
| Push notification | ❌ | (C) FCM para mobile |
| Email | ❌ | SMTP para alertas críticos |

---

### 1.7 Expense (Despesa) — NÃO EXISTE

| Regra de Negócio | Status | Observação |
|------------------|--------|------------|
| Entidade | ❌ | Precisa criar |
| vehicleId | ❌ | Veículo associado |
| tripId | ❌ | Viagem associada (opcional) |
| Tipo | ⚠️ Existe enum | typeCost: FUEL, TOLL, PARKING, OTHER |
| Valor | ❌ | Campo amount |
| Data | ❌ | Campo date |
| Descrição | ❌ | Campo description |
| Comprovante | ❌ | (C) Upload de imagem |
| Evento | ❌ | expense.registered |

---

### 1.8 Report (Relatório) — NÃO EXISTE

| Regra de Negócio | Status | Observação |
|------------------|--------|------------|
| Custo mensal por veículo | ❌ | Agregação de expenses |
| Km rodado por período | ❌ | Agregação de trips |
| Manutenções por período | ❌ | Agregação de maintenances |
| Export CSV | ❌ | Job assíncrono |
| Dashboard KPIs | ❌ | Total veículos, manutenções pendentes, viagens hoje |

---

## 2. Regras de Negócio Transversais

### 2.1 Autenticação e Autorização
| Regra | Status |
|-------|--------|
| OAuth2 com Keycloak | ❌ Não implementado |
| JWT validation | ❌ |
| Roles: ADMIN, FLEET_MANAGER, DRIVER | ❌ (enum existe parcial) |
| RBAC por endpoint | ❌ |
| DRIVER só vê próprias viagens | ❌ |

### 2.2 Comunicação
| Regra | Status |
|-------|--------|
| REST síncrono (validações) | ✅ Existe |
| RabbitMQ assíncrono (side effects) | ❌ |
| Transactional Outbox | ❌ |
| Eventos de domínio | ❌ |

### 2.3 Resiliência
| Regra | Status |
|-------|--------|
| DLQ (Dead Letter Queue) | ❌ |
| Idempotência | ❌ |
| Circuit Breaker | ❌ |
| Retry 3x | ❌ |

### 2.4 Observabilidade
| Regra | Status |
|-------|--------|
| X-Request-Id | ❌ |
| Logs estruturados JSON | ❌ (usa pattern simple) |
| Actuator health | ✅ Configurado |
| Métricas | ❌ |

---

## 3. Resumo: O que Falta no Core

### Crítico (Épico 0 — deve ter)
1. ~~UUID~~ (decidido)
2. ~~compose.yaml MySQL~~ (decidido)
3. ~~PUT bug~~ (decidido)
4. ~~Sort bug~~ (decidido)
5. ~~Migrations UUID~~ (decidido)
6. ~~Testes~~ (decidido)
7. ~~Contratos OpenAPI~~ (decidido)
8. **Renomear Manutention → Maintenance**
9. **Validação de placa Mercosul**
10. **Filtros combinados vehicle (plate, brand, status)**
11. **Tratamento 409 para placa duplicada**
12. **PATCH /vehicles/{id}/status para inativação**

### Importante (Épico 2 — microsserviços)
13. **Driver entity + CRUD**
14. **VehicleAssignment entity + regras**
15. **Trip entity + fluxo iniciar/finalizar**
16. **Validação vehicleId via REST**
17. **RBAC por role**

### Nice-to-have (Épico 3-4)
18. Notification service
19. Expense service
20. Report service
21. Eventos de domínio
22. Outbox pattern

---

## 4. Regras de Validação Detalhadas

### Vehicle
```
POST /vehicles:
  plate: obrigatório, formato Mercosul [A-Z]{3}\d[A-Z]\d{2} ou antigo [A-Z]{3}-\d{4}
  plate: único no sistema (409 se duplicado)
  model: obrigatório, max 100 chars
  brand: obrigatório, max 100 chars
  year: obrigatório, 1900 <= year <= current_year + 1
  color: opcional, max 50 chars
  mileage: obrigatório, >= 0
  status: default ACTIVE
```

### Maintenance
```
POST /maintenances:
  vehicleId: obrigatório, deve existir e estar ACTIVE
  description: obrigatório, texto livre
  type: obrigatório, enum (MANUTENTION, FUEL, TOLL, PARKING, OTHER)
  cost: obrigatório, > 0
  mileage: obrigatório, >= 0
  nextMileage: obrigatório, > mileage atual
  maintenanceDate: obrigatório
  done: default PENDING
```

### Driver
```
POST /drivers:
  name: obrigatório
  cpf: obrigatório, 11 dígitos, válido
  cnh: obrigatório, 11 dígitos
  category: obrigatório, enum (A, B, C, D, E)
  expiryDate: obrigatório, > today
  phone: opcional
  email: opcional, formato válido
```

### Trip
```
POST /trips/start:
  vehicleId: obrigatório, deve ter assignment ativo com o driver
  origin: obrigatório
  startKm: obrigatório, >= 0

PUT /trips/{id}/complete:
  destination: obrigatório
  endKm: obrigatório, > startKm
  distance = endKm - startKm
```
