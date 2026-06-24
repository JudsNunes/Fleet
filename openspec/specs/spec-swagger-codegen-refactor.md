# Spec: Refatoração do Fluxo de Geração Swagger Codegen

**ID**: SPECS-2026-001
**Status**: Draft
**Autor**: Lucas Nunes
**Data**: 2026-06-24

---

## [S1] Problema

O OpenAPI spec (`evolutech-fleet.yaml`) define endpoints apenas para Vehicle, Maintenance e Driver. Porém, 5 controllers existentes no projeto referenciam interfaces e DTOs gerados que não existem:

| Controller | Interface Gerada Ausente | DTOs Ausentes |
|------------|--------------------------|---------------|
| `AuditLogController` | `AuditLogsApi` | `AuditLogPageDTO`, `AuditLogDTO` |
| `FineController` | `FinesApi` | `FineDTO`, `FinePageDTO`, `FineRequestDTO` |
| `ServiceOrderController` | `ServiceOrdersApi` | `ServiceOrderDTO`, `ServiceOrderPageDTO`, `ServiceOrderRequestDTO`, `ServiceOrderApprovalDTO` |
| `TripController` | *(manual, sem interface)* | *(usa DTOs internos diretamente)* |
| `VehicleAssignmentController` | *(manual, sem interface)* | *(usa DTOs internos diretamente)* |

**Resultado**: Erros de compilação em `AuditLogController`, `FineController`, `ServiceOrderController` e `ApiMapper`. O `DriverController` também não implementa `DriversApi` — usa mapeamento manual com `@RequestMapping("/drivers")`.

---

## [S2] Escopo

### Incluído
1. Adicionar definições de AuditLog, Fine e ServiceOrder ao OpenAPI spec
2. Refatorar controllers para implementar as interfaces geradas
3. Atualizar `ApiMapper` para mapear os novos DTOs gerados
4. Padronizar DriverController para implementar `DriversApi`
5. Adicionar endpoints de Trip e Assignment ao spec e refatorar controllers

### Não-incluído
- Alterações em entidades, services ou repositories existentes
- Novos endpoints além dos que já existem nos controllers
- Migrações de banco de dados
- Testes unitários/integração (fase posterior)

---

## [S3] Padrão de Naming (Convenções)

O plugin `openapi-generator-maven-plugin` aplica automaticamente o suffixo `DTO` aos schemas gerados. Portanto:

| Schema OpenAPI | Classe Gerada |
|----------------|---------------|
| `AuditLog` | `AuditLogDTO` |
| `AuditLogPage` | `AuditLogPageDTO` |
| `Fine` | `FineDTO` |
| `FinePage` | `FinePageDTO` |
| `FineRequest` | `FineRequestDTO` |
| `ServiceOrder` | `ServiceOrderDTO` |
| `ServiceOrderPage` | `ServiceOrderPageDTO` |
| `ServiceOrderRequest` | `ServiceOrderRequestDTO` |
| `ServiceOrderApproval` | `ServiceOrderApprovalDTO` |
| `DriverPage` | `DriverPageDTO` |
| `DriverRequest` | `DriverRequestDTO` |

Interfaces geradas seguem o padrão `{Tag}Api` (pluralizado pelo codegen).

---

## [S4] Schemas a Adicionar ao OpenAPI

### AuditLog

```yaml
AuditLog:
  type: object
  properties:
    id:
      type: string
      format: uuid
    entityType:
      type: string
    entityId:
      type: string
      format: uuid
    action:
      type: string
      enum: [CREATE, UPDATE, DELETE, STATUS_CHANGE]
    fieldName:
      type: string
    oldValue:
      type: string
    newValue:
      type: string
    userId:
      type: string
    timestamp:
      type: string
      format: date-time
    ipAddress:
      type: string
    createdAt:
      type: string
      format: date-time
```

### Fine

```yaml
Fine:
  type: object
  properties:
    id:
      type: string
      format: uuid
    vehicleId:
      type: string
      format: uuid
    driverCpf:
      type: string
    description:
      type: string
    amount:
      type: number
      format: double
    infractionDate:
      type: string
      format: date
    points:
      type: integer
    status:
      type: string
      enum: [PENDING, PAID, DISPUTED]
    costCenterId:
      type: string
    createdAt:
      type: string
      format: date-time

FineRequest:
  type: object
  required:
    - vehicleId
    - driverCpf
    - description
    - amount
    - infractionDate
    - status
  properties:
    vehicleId:
      type: string
      format: uuid
    driverCpf:
      type: string
      pattern: "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"
    description:
      type: string
    amount:
      type: number
      format: double
      exclusiveMinimum: true
    infractionDate:
      type: string
      format: date
    points:
      type: integer
    status:
      type: string
      enum: [PENDING, PAID, DISPUTED]
    costCenterId:
      type: string
```

### ServiceOrder

```yaml
ServiceOrder:
  type: object
  properties:
    id:
      type: string
      format: uuid
    vehicleId:
      type: string
      format: uuid
    description:
      type: string
    status:
      type: string
      enum: [PENDING, APPROVED, REJECTED, COMPLETED]
    approvedBy:
      type: string
    approvedAt:
      type: string
      format: date-time
    warrantyExpiryDate:
      type: string
      format: date-time
    createdAt:
      type: string
      format: date-time
    updatedAt:
      type: string
      format: date-time

ServiceOrderRequest:
  type: object
  required:
    - vehicleId
    - description
  properties:
    vehicleId:
      type: string
      format: uuid
    description:
      type: string
    warrantyExpiryDate:
      type: string
      format: date-time

ServiceOrderApproval:
  type: object
  required:
    - approvedBy
  properties:
    approvedBy:
      type: string
```

---

## [S5] Endpoints a Adicionar ao OpenAPI

### AuditLog (tag: `AuditLog`)

| Método | Path | operationId | Parâmetros | Resposta |
|--------|------|-------------|------------|----------|
| GET | `/audit-logs` | `getAllAuditLogs` | page, size (query) | `200`: `AuditLogPage` |
| GET | `/audit-logs/entity/{entityType}` | `getAuditLogsByEntityType` | entityType (path), page, size | `200`: `AuditLogPage` |
| GET | `/audit-logs/entity/{entityType}/{entityId}` | `getAuditLogsByEntity` | entityType, entityId (path), page, size | `200`: `AuditLogPage` |
| GET | `/audit-logs/user/{userId}` | `getAuditLogsByUser` | userId (path), page, size | `200`: `AuditLogPage` |

### Fine (tag: `Fine`)

| Método | Path | operationId | Parâmetros | Resposta |
|--------|------|-------------|------------|----------|
| GET | `/fines` | `getAllFines` | page, size (query) | `200`: `FinePage` |
| POST | `/fines` | `createFine` | body: `FineRequest` | `201`: `Fine`, `400`: error |
| GET | `/fines/{id}` | `getFineById` | id (path, uuid) | `200`: `Fine`, `404`: error |
| PUT | `/fines/{id}` | `updateFine` | id (path), body: `FineRequest` | `200`: void, `404`: error |
| DELETE | `/fines/{id}` | `deleteFine` | id (path) | `204`: void, `404`: error |
| GET | `/fines/driver/{driverCpf}` | `getFinesByDriverCpf` | driverCpf (path), page, size | `200`: `FinePage` |
| GET | `/fines/status/{status}` | `getFinesByStatus` | status (path, enum), page, size | `200`: `FinePage` |
| GET | `/fines/vehicle/{vehicleId}` | `getFinesByVehicle` | vehicleId (path, uuid), page, size | `200`: `FinePage` |

### ServiceOrder (tag: `ServiceOrder`)

| Método | Path | operationId | Parâmetros | Resposta |
|--------|------|-------------|------------|----------|
| GET | `/service-orders` | `getAllServiceOrders` | page, size (query) | `200`: `ServiceOrderPage` |
| POST | `/service-orders` | `createServiceOrder` | body: `ServiceOrderRequest` | `201`: `ServiceOrder`, `400`: error |
| GET | `/service-orders/{id}` | `getServiceOrderById` | id (path, uuid) | `200`: `ServiceOrder`, `404`: error |
| PUT | `/service-orders/{id}` | `updateServiceOrder` | id (path), body: `ServiceOrderRequest` | `200`: void, `404`: error |
| DELETE | `/service-orders/{id}` | `deleteServiceOrder` | id (path) | `204`: void, `404`: error |
| PATCH | `/service-orders/{id}/approve` | `approveServiceOrder` | id (path), body: `ServiceOrderApproval` | `200`: `ServiceOrder` |
| PATCH | `/service-orders/{id}/reject` | `rejectServiceOrder` | id (path), body: `ServiceOrderApproval` | `200`: `ServiceOrder` |
| PATCH | `/service-orders/{id}/complete` | `completeServiceOrder` | id (path) | `200`: `ServiceOrder` |
| GET | `/service-orders/status/{status}` | `getServiceOrdersByStatus` | status (path, enum), page, size | `200`: `ServiceOrderPage` |
| GET | `/service-orders/vehicle/{vehicleId}` | `getServiceOrdersByVehicle` | vehicleId (path, uuid), page, size | `200`: `ServiceOrderPage` |
| GET | `/service-orders/warranties/vehicle/{vehicleId}` | `getActiveWarranties` | vehicleId (path, uuid) | `200`: array of `ServiceOrder` |

### Trip (tag: `Trip`)

| Método | Path | operationId | Parâmetros | Resposta |
|--------|------|-------------|------------|----------|
| GET | `/trips` | `getAllTrips` | vehicleId, driverId, status (query), page, size | `200`: `TripPage` |
| POST | `/trips` | `createTrip` | body: `TripRequest` | `201`: `Trip` |
| GET | `/trips/{id}` | `getTripById` | id (path) | `200`: `Trip` |
| PUT | `/trips/{id}` | `updateTrip` | id (path), body: `TripRequest` | `200`: `Trip` |
| DELETE | `/trips/{id}` | `deleteTrip` | id (path) | `204` |
| PATCH | `/trips/{id}/start` | `startTrip` | id, body: `{startMileage}` | `200`: `Trip` |
| PATCH | `/trips/{id}/complete` | `completeTrip` | id, body: `{endMileage}` | `200`: `Trip` |
| PATCH | `/trips/{id}/cancel` | `cancelTrip` | id | `200`: `Trip` |
| PATCH | `/trips/{id}/deviation` | `registerDeviation` | id, body: `{justification}` | `200`: `Trip` |
| GET | `/trips/vehicle/{vehicleId}` | `getTripsByVehicle` | vehicleId (path), page, size | `200`: `TripPage` |
| GET | `/trips/driver/{driverId}` | `getTripsByDriver` | driverId (path), page, size | `200`: `TripPage` |

### VehicleAssignment (tag: `VehicleAssignment`)

| Método | Path | operationId | Parâmetros | Resposta |
|--------|------|-------------|------------|----------|
| GET | `/assignments` | `getAllAssignments` | vehicleId, driverId, status (query), page, size | `200`: `AssignmentPage` |
| POST | `/assignments` | `createAssignment` | body: `AssignmentRequest` | `201`: `Assignment` |
| GET | `/assignments/{id}` | `getAssignmentById` | id (path) | `200`: `Assignment` |
| PUT | `/assignments/{id}` | `updateAssignment` | id (path), body: `AssignmentRequest` | `200`: `Assignment` |
| DELETE | `/assignments/{id}` | `deleteAssignment` | id (path) | `204` |
| PATCH | `/assignments/{id}/end` | `endAssignment` | id (path) | `200`: `Assignment` |
| GET | `/assignments/vehicle/{vehicleId}` | `getAssignmentsByVehicle` | vehicleId (path), page, size | `200`: `AssignmentPage` |
| GET | `/assignments/driver/{driverId}` | `getAssignmentsByDriver` | driverId (path), page, size | `200`: `AssignmentPage` |
| GET | `/assignments/active` | `getActiveAssignments` | — | `200`: array of `Assignment` |

---

## [S6] Controller Refactoring

### Padrão Atual (Vehicle, Maintenance)

Os controllers de Vehicle e Maintenance implementam a interface gerada pelo codegen:

```java
@RestController
@RequiredArgsConstructor
public class VehicleController implements VehiclesApi {
    private final VehicleService vehicleService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<VehicleDTO> createVehicle(VehicleRequestDTO vehicleRequestDTO) { ... }
}
```

### Controllers a Refatorar

#### AuditLogController
- Atual: `implements AuditLogsApi` (já tenta, mas a interface não existe)
- Ação: Adicionar endpoints ao OpenAPI para gerar `AuditLogsApi`
- Mapper: Métodos `toAuditLogApiFromInternal` e `toAuditLogPageApi` já existem no `ApiMapper`

#### FineController
- Atual: `implements FinesApi` (já tenta, mas a interface não existe)
- Ação: Adicionar endpoints ao OpenAPI para gerar `FinesApi`
- Mapper: Métodos `toFineRequest`, `toFineApi`, `toFinePageApi` já existem no `ApiMapper`

#### ServiceOrderController
- Atual: `implements ServiceOrdersApi` (já tenta, mas a interface não existe)
- Ação: Adicionar endpoints ao OpenAPI para gerar `ServiceOrdersApi`
- Mapper: Métodos `toServiceOrderRequest`, `toServiceOrderApi`, `toServiceOrderPageApi`, `toServiceOrderApproval` já existem no `ApiMapper`

#### DriverController
- Atual: **NÃO implementa `DriversApi`** — usa `@RequestMapping("/drivers")` manual
- Ação: Refatorar para `implements DriversApi`
- Endpoint `/expiring-cnhs` já existe no OpenAPI e na interface gerada — será coberto pela implementação

#### TripController
- Atual: **Manual** com `@RequestMapping("/trips")`
- Ação: Adicionar schemas/paths ao OpenAPI, refatorar para `implements TripsApi`
- Necessário: Criar mapeamento entre `TripRequestDTO` interno e `TripRequest` gerado

#### VehicleAssignmentController
- Atual: **Manual** com `@RequestMapping("/assignments")`
- Ação: Adicionar schemas/paths ao OpenAPI, refatorar para `implements VehicleAssignmentsApi`
- Necessário: Criar mapeamento entre request/response interno e gerado

---

## [S7] Atualização do ApiMapper

### Métodos já existentes (não alterar)
- `toVehicleRequest`, `toVehicleApi`, `toVehiclePageApi`, `toVehicleStatus`
- `toMaintenanceRequest`, `toMaintenanceApi`, `toMaintenancePageApi`
- `toAuditLogApiFromInternal`, `toAuditLogPageApi`
- `toFineRequest`, `toFineApi`, `toFinePageApi`
- `toServiceOrderRequest`, `toServiceOrderApi`, `toServiceOrderPageApi`, `toServiceOrderApproval`

### Métodos a adicionar
- `toDriverRequest(DriverRequestDTO api) → DriverRequestDTO interno`
- `toDriverApi(DriverResponseDTO internal) → DriverDTO gerado`
- `toDriverPageApi(Page<DriverResponseDTO>) → DriverPageDTO`
- `toTripRequest(TripRequestDTO api) → TripRequestDTO interno`
- `toTripApi(TripResponseDTO internal) → TripDTO gerado`
- `toTripPageApi(Page<TripResponseDTO>) → TripPageDTO`
- `toAssignmentRequest(AssignmentRequestDTO api) → VehicleAssignmentRequestDTO interno`
- `toAssignmentApi(VehicleAssignmentResponseDTO internal) → AssignmentDTO gerado`
- `toAssignmentPageApi(Page<VehicleAssignmentResponseDTO>) → AssignmentPageDTO`

---

## [S8] Tags OpenAPI

Adicionar ao bloco `tags`:

```yaml
tags:
  - name: Vehicle
    description: Operações com veículos
  - name: Maintenance
    description: Operações com manutenções
  - name: Driver
    description: Gestão de motoristas
  - name: AuditLog
    description: Consulta de logs de auditoria
  - name: Fine
    description: Gestão de multas
  - name: ServiceOrder
    description: Gestão de ordens de serviço
  - name: Trip
    description: Gestão de viagens
  - name: VehicleAssignment
    description: Gestão de alocações de veículos
```

---

## [S9] Ordem de Implementação

1. Adicionar schemas e endpoints de **AuditLog** ao `evolutech-fleet.yaml`
2. Adicionar schemas e endpoints de **Fine**
3. Adicionar schemas e endpoints de **ServiceOrder**
4. Executar `mvn generate-sources` para gerar interfaces e DTOs
5. Refatorar `AuditLogController`, `FineController`, `ServiceOrderController` (já implementam a interface)
6. Adicionar schemas e endpoints de **Driver** (atualizar spec existente se necessário)
7. Refatorar `DriverController` para `implements DriversApi`
8. Adicionar schemas e endpoints de **Trip** e **VehicleAssignment**
9. Refatorar `TripController` e `VehicleAssignmentController`
10. Adicionar métodos de mapeamento ao `ApiMapper`
11. Executar `mvn compile` para validar compilação

---

## [S10] Riscos e Mitigações

| Risco | Impacto | Mitigação |
|-------|---------|-----------|
| Codegen gera assinatura diferente do esperado pelos controllers | Controllers não implementam a interface | Verificar assinaturas geradas antes de refatorar controllers |
| Conflito de endpoints entre controllers manuais e gerados | Dupla mapeamento de rotas | Remover `@RequestMapping` dos controllers que passam a implementar interfaces |
| Trip e Assignment têm DTOs internos complexos | Mapeamento繁琐 | Criar métodos dedicados no ApiMapper, seguir padrão existente |
| Campo `serviceOrderId` na MaintenanceEntity não exposto no spec | Dados incompletos | Adicionar ao schema Maintenance se necessário (fase posterior) |
