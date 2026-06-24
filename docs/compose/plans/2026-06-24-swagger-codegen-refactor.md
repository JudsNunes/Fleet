# Swagger Codegen Refactoring Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use compose:subagent (recommended) or compose:execute to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Fix compilation errors by adding missing domain definitions to the OpenAPI spec and refactoring all controllers to implement codegen-generated interfaces.

**Architecture:** Single OpenAPI spec (`evolutech-fleet.yaml`) drives code generation via `openapi-generator-maven-plugin` (spring, interfaceOnly=true, modelNameSuffix=DTO). Controllers implement generated interfaces; ApiMapper bridges generated DTOs to internal DTOs.

**Tech Stack:** Java 17, Spring Boot 4.0.2, OpenAPI Generator Maven Plugin 7.5.0, Lombok, Maven

## Global Constraints

- Plugin config: `generatorName=spring`, `apiPackage=com.evolutech.fleet.api`, `modelPackage=com.evolutech.fleet.api.model`, `modelNameSuffix=DTO`, `interfaceOnly=true`
- Generated interfaces: `{Tag}Api` (pluralized by codegen)
- Generated DTOs: `{SchemaName}DTO` (suffix applied by plugin)
- Controller pattern: `@RestController` + `implements {Tag}Api` + `ApiMapper` for conversion
- Package: `com.evolutech.core.fleet.API` for controllers
- Do NOT modify existing internal DTOs, services, entities, or repositories
- Do NOT modify pom.xml plugin config

---

## File Map

| Action | File |
|--------|------|
| Modify | `src/main/resources/api/evolutech-fleet.yaml` |
| Modify | `src/main/java/com/evolutech/core/fleet/mapper/ApiMapper.java` |
| Modify | `src/main/java/com/evolutech/core/fleet/API/AuditLogController.java` |
| Modify | `src/main/java/com/evolutech/core/fleet/API/FineController.java` |
| Modify | `src/main/java/com/evolutech/core/fleet/API/ServiceOrderController.java` |
| Modify | `src/main/java/com/evolutech/core/fleet/API/DriverController.java` |
| Modify | `src/main/java/com/evolutech/core/fleet/API/TripController.java` |
| Modify | `src/main/java/com/evolutech/core/fleet/API/VehicleAssignmentController.java` |

---

### Task 1: Add AuditLog definitions to OpenAPI spec

**Covers:** [S4, S5, S8]

**Files:**
- Modify: `src/main/resources/api/evolutech-fleet.yaml`

**Interfaces:**
- Produces: `AuditLogsApi` interface, `AuditLogDTO`, `AuditLogPageDTO` classes (generated)

- [ ] **Step 1: Add AuditLog tag**

Add to the `tags:` section:

```yaml
  - name: AuditLog
    description: Consulta de logs de auditoria
```

- [ ] **Step 2: Add AuditLog paths**

Add after the Driver paths (before `components:`):

```yaml
  /audit-logs:
    get:
      tags:
        - AuditLog
      summary: Listar todos os logs de auditoria
      operationId: getAllAuditLogs
      parameters:
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Lista de logs de auditoria
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/AuditLogPage'

  /audit-logs/entity/{entityType}:
    get:
      tags:
        - AuditLog
      summary: Buscar logs por tipo de entidade
      operationId: getAuditLogsByEntityType
      parameters:
        - name: entityType
          in: path
          required: true
          schema:
            type: string
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Logs encontrados
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/AuditLogPage'

  /audit-logs/entity/{entityType}/{entityId}:
    get:
      tags:
        - AuditLog
      summary: Buscar logs por entidade específica
      operationId: getAuditLogsByEntity
      parameters:
        - name: entityType
          in: path
          required: true
          schema:
            type: string
        - name: entityId
          in: path
          required: true
          schema:
            type: string
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Logs encontrados
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/AuditLogPage'

  /audit-logs/user/{userId}:
    get:
      tags:
        - AuditLog
      summary: Buscar logs por usuário
      operationId: getAuditLogsByUser
      parameters:
        - name: userId
          in: path
          required: true
          schema:
            type: string
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Logs encontrados
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/AuditLogPage'
```

- [ ] **Step 3: Add AuditLog schemas**

Add to `components.schemas:` section:

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

    AuditLogPage:
      type: object
      properties:
        content:
          type: array
          items:
            $ref: '#/components/schemas/AuditLog'
        totalElements:
          type: integer
        totalPages:
          type: integer
        size:
          type: integer
        number:
          type: integer
```

- [ ] **Step 4: Commit**

```bash
git add src/main/resources/api/evolutech-fleet.yaml
git commit -m "spec: add AuditLog definitions to OpenAPI spec"
```

---

### Task 2: Add Fine definitions to OpenAPI spec

**Covers:** [S4, S5]

**Files:**
- Modify: `src/main/resources/api/evolutech-fleet.yaml`

**Interfaces:**
- Produces: `FinesApi` interface, `FineDTO`, `FinePageDTO`, `FineRequestDTO` classes (generated)

- [ ] **Step 1: Add Fine tag**

Add to `tags:` section:

```yaml
  - name: Fine
    description: Gestão de multas
```

- [ ] **Step 2: Add Fine paths**

Add after AuditLog paths:

```yaml
  /fines:
    get:
      tags:
        - Fine
      summary: Listar todas as multas
      operationId: getAllFines
      parameters:
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Lista de multas
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/FinePage'
    post:
      tags:
        - Fine
      summary: Criar uma nova multa
      operationId: createFine
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/FineRequest'
      responses:
        '201':
          description: Multa criada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Fine'
        '400':
          description: Dados inválidos

  /fines/{id}:
    get:
      tags:
        - Fine
      summary: Buscar multa por ID
      operationId: getFineById
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      responses:
        '200':
          description: Multa encontrada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Fine'
        '404':
          description: Multa não encontrada
    put:
      tags:
        - Fine
      summary: Atualizar uma multa
      operationId: updateFine
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/FineRequest'
      responses:
        '200':
          description: Multa atualizada
        '404':
          description: Multa não encontrada
    delete:
      tags:
        - Fine
      summary: Deletar uma multa
      operationId: deleteFine
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      responses:
        '204':
          description: Multa deletada
        '404':
          description: Multa não encontrada

  /fines/driver/{driverCpf}:
    get:
      tags:
        - Fine
      summary: Buscar multas por CPF do motorista
      operationId: getFinesByDriverCpf
      parameters:
        - name: driverCpf
          in: path
          required: true
          schema:
            type: string
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Multas encontradas
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/FinePage'

  /fines/status/{status}:
    get:
      tags:
        - Fine
      summary: Buscar multas por status
      operationId: getFinesByStatus
      parameters:
        - name: status
          in: path
          required: true
          schema:
            type: string
            enum: [PENDING, PAID, DISPUTED]
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Multas encontradas
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/FinePage'

  /fines/vehicle/{vehicleId}:
    get:
      tags:
        - Fine
      summary: Buscar multas por veículo
      operationId: getFinesByVehicle
      parameters:
        - name: vehicleId
          in: path
          required: true
          schema:
            type: string
            format: uuid
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Multas encontradas
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/FinePage'
```

- [ ] **Step 3: Add Fine schemas**

Add to `components.schemas:` section:

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

    FinePage:
      type: object
      properties:
        content:
          type: array
          items:
            $ref: '#/components/schemas/Fine'
        totalElements:
          type: integer
        totalPages:
          type: integer
        size:
          type: integer
        number:
          type: integer
```

- [ ] **Step 4: Commit**

```bash
git add src/main/resources/api/evolutech-fleet.yaml
git commit -m "spec: add Fine definitions to OpenAPI spec"
```

---

### Task 3: Add ServiceOrder definitions to OpenAPI spec

**Covers:** [S4, S5]

**Files:**
- Modify: `src/main/resources/api/evolutech-fleet.yaml`

**Interfaces:**
- Produces: `ServiceOrdersApi` interface, `ServiceOrderDTO`, `ServiceOrderPageDTO`, `ServiceOrderRequestDTO`, `ServiceOrderApprovalDTO` classes (generated)

- [ ] **Step 1: Add ServiceOrder tag**

Add to `tags:` section:

```yaml
  - name: ServiceOrder
    description: Gestão de ordens de serviço
```

- [ ] **Step 2: Add ServiceOrder paths**

Add after Fine paths:

```yaml
  /service-orders:
    get:
      tags:
        - ServiceOrder
      summary: Listar todas as ordens de serviço
      operationId: getAllServiceOrders
      parameters:
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: Lista de ordens de serviço
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServiceOrderPage'
    post:
      tags:
        - ServiceOrder
      summary: Criar uma nova ordem de serviço
      operationId: createServiceOrder
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ServiceOrderRequest'
      responses:
        '201':
          description: OS criada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServiceOrder'
        '400':
          description: Dados inválidos

  /service-orders/{id}:
    get:
      tags:
        - ServiceOrder
      summary: Buscar OS por ID
      operationId: getServiceOrderById
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      responses:
        '200':
          description: OS encontrada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServiceOrder'
        '404':
          description: OS não encontrada
    put:
      tags:
        - ServiceOrder
      summary: Atualizar OS
      operationId: updateServiceOrder
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ServiceOrderRequest'
      responses:
        '200':
          description: OS atualizada
        '404':
          description: OS não encontrada
    delete:
      tags:
        - ServiceOrder
      summary: Deletar OS (soft-delete)
      operationId: deleteServiceOrder
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      responses:
        '204':
          description: OS deletada
        '404':
          description: OS não encontrada

  /service-orders/{id}/approve:
    patch:
      tags:
        - ServiceOrder
      summary: Aprovar OS
      operationId: approveServiceOrder
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ServiceOrderApproval'
      responses:
        '200':
          description: OS aprovada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServiceOrder'

  /service-orders/{id}/reject:
    patch:
      tags:
        - ServiceOrder
      summary: Rejeitar OS
      operationId: rejectServiceOrder
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ServiceOrderApproval'
      responses:
        '200':
          description: OS rejeitada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServiceOrder'

  /service-orders/{id}/complete:
    patch:
      tags:
        - ServiceOrder
      summary: Concluir OS
      operationId: completeServiceOrder
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      responses:
        '200':
          description: OS concluída
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServiceOrder'

  /service-orders/status/{status}:
    get:
      tags:
        - ServiceOrder
      summary: Buscar OS por status
      operationId: getServiceOrdersByStatus
      parameters:
        - name: status
          in: path
          required: true
          schema:
            type: string
            enum: [PENDING, APPROVED, REJECTED, COMPLETED]
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: OS encontradas
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServiceOrderPage'

  /service-orders/vehicle/{vehicleId}:
    get:
      tags:
        - ServiceOrder
      summary: Buscar OS por veículo
      operationId: getServiceOrdersByVehicle
      parameters:
        - name: vehicleId
          in: path
          required: true
          schema:
            type: string
            format: uuid
        - name: page
          in: query
          schema:
            type: integer
            default: 0
        - name: size
          in: query
          schema:
            type: integer
            default: 20
      responses:
        '200':
          description: OS encontradas
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServiceOrderPage'

  /service-orders/warranties/vehicle/{vehicleId}:
    get:
      tags:
        - ServiceOrder
      summary: Buscar garantias ativas por veículo
      operationId: getActiveWarranties
      parameters:
        - name: vehicleId
          in: path
          required: true
          schema:
            type: string
            format: uuid
      responses:
        '200':
          description: Garantias ativas
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/ServiceOrder'
```

- [ ] **Step 3: Add ServiceOrder schemas**

Add to `components.schemas:` section:

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

    ServiceOrderPage:
      type: object
      properties:
        content:
          type: array
          items:
            $ref: '#/components/schemas/ServiceOrder'
        totalElements:
          type: integer
        totalPages:
          type: integer
        size:
          type: integer
        number:
          type: integer
```

- [ ] **Step 4: Commit**

```bash
git add src/main/resources/api/evolutech-fleet.yaml
git commit -m "spec: add ServiceOrder definitions to OpenAPI spec"
```

---

### Task 4: Add Trip and VehicleAssignment definitions to OpenAPI spec

**Covers:** [S4, S5, S8]

**Files:**
- Modify: `src/main/resources/api/evolutech-fleet.yaml`

**Interfaces:**
- Produces: `TripsApi`, `VehicleAssignmentsApi` interfaces and their DTOs (generated)

- [ ] **Step 1: Add Trip and VehicleAssignment tags**

Add to `tags:` section:

```yaml
  - name: Trip
    description: Gestão de viagens
  - name: VehicleAssignment
    description: Gestão de alocações de veículos
```

- [ ] **Step 2: Add Trip paths**

Add after ServiceOrder paths. Need to reference existing entities `TripRequest`, `Trip`, `TripPage`. Trip statuses: use `TripStatus` enum values. Check `TripEntity` for fields.

Add Trip paths (CRUD + workflow endpoints). Key operationIds:
- `getAllTrips` (GET /trips) — params: vehicleId, driverId, status, page, size
- `createTrip` (POST /trips)
- `getTripById` (GET /trips/{id})
- `updateTrip` (PUT /trips/{id})
- `deleteTrip` (DELETE /trips/{id})
- `startTrip` (PATCH /trips/{id}/start)
- `completeTrip` (PATCH /trips/{id}/complete)
- `cancelTrip` (PATCH /trips/{id}/cancel)
- `registerDeviation` (PATCH /trips/{id}/deviation)
- `getTripsByVehicle` (GET /trips/vehicle/{vehicleId})
- `getTripsByDriver` (GET /trips/driver/{driverId})

- [ ] **Step 3: Add VehicleAssignment paths**

Key operationIds:
- `getAllAssignments` (GET /assignments) — params: vehicleId, driverId, status, page, size
- `createAssignment` (POST /assignments)
- `getAssignmentById` (GET /assignments/{id})
- `updateAssignment` (PUT /assignments/{id})
- `deleteAssignment` (DELETE /assignments/{id})
- `endAssignment` (PATCH /assignments/{id}/end)
- `getAssignmentsByVehicle` (GET /assignments/vehicle/{vehicleId})
- `getAssignmentsByDriver` (GET /assignments/driver/{driverId})
- `getActiveAssignments` (GET /assignments/active)

- [ ] **Step 4: Add Trip and VehicleAssignment schemas**

Add schemas: `Trip`, `TripRequest`, `TripPage`, `VehicleAssignment`, `VehicleAssignmentRequest`, `VehicleAssignmentPage`

- [ ] **Step 5: Commit**

```bash
git add src/main/resources/api/evolutech-fleet.yaml
git commit -m "spec: add Trip and VehicleAssignment definitions to OpenAPI spec"
```

---

### Task 5: Generate code and verify interfaces exist

**Covers:** [S3]

**Files:**
- Generated: `target/generated-sources/openapi/`

**Interfaces:**
- Produces: All generated API interfaces and DTO classes

- [ ] **Step 1: Run OpenAPI code generation**

```bash
mvn generate-sources -pl . -q
```

Expected: BUILD SUCCESS. Generated files in `target/generated-sources/openapi/src/main/java/com/evolutech/fleet/api/`

- [ ] **Step 2: Verify generated interfaces exist**

```bash
ls target/generated-sources/openapi/src/main/java/com/evolutech/fleet/api/
```

Expected files: `AuditLogsApi.java`, `FinesApi.java`, `ServiceOrdersApi.java`, `TripsApi.java`, `VehicleAssignmentsApi.java`, `VehiclesApi.java`, `MaintenancesApi.java`, `DriversApi.java`, `ApiUtil.java`

- [ ] **Step 3: Verify generated DTOs exist**

```bash
ls target/generated-sources/openapi/src/main/java/com/evolutech/fleet/api/model/
```

Expected to include: `AuditLogDTO.java`, `AuditLogPageDTO.java`, `FineDTO.java`, `FinePageDTO.java`, `FineRequestDTO.java`, `ServiceOrderDTO.java`, `ServiceOrderPageDTO.java`, `ServiceOrderRequestDTO.java`, `ServiceOrderApprovalDTO.java`, `TripDTO.java`, `TripPageDTO.java`, `TripRequestDTO.java`, `VehicleAssignmentDTO.java`, `VehicleAssignmentPageDTO.java`, `VehicleAssignmentRequestDTO.java`

- [ ] **Step 4: Commit generated code**

```bash
git add target/generated-sources/openapi/
git commit -m "chore: generate OpenAPI interfaces and DTOs"
```

---

### Task 6: Verify AuditLogController compiles

**Covers:** [S6, S7]

**Files:**
- No changes needed — `AuditLogController.java` already implements `AuditLogsApi`

**Interfaces:**
- Consumes: `AuditLogsApi` (generated), `AuditLogPageDTO` (generated), `ApiMapper.toAuditLogPageApi()`

- [ ] **Step 1: Attempt compilation of AuditLogController**

```bash
mvn compile -pl . -q 2>&1 | grep -i "AuditLog"
```

Expected: No errors related to AuditLog (interface and DTOs now exist)

- [ ] **Step 2: If compilation passes, commit**

```bash
git add src/main/java/com/evolutech/core/fleet/API/AuditLogController.java
git commit -m "fix: AuditLogController compiles with generated AuditLogsApi"
```

---

### Task 7: Verify FineController compiles

**Covers:** [S6, S7]

**Files:**
- No changes needed — `FineController.java` already implements `FinesApi`

**Interfaces:**
- Consumes: `FinesApi` (generated), `FineDTO`, `FinePageDTO`, `FineRequestDTO` (generated), `ApiMapper.toFineRequest()`, `ApiMapper.toFineApi()`, `ApiMapper.toFinePageApi()`

- [ ] **Step 1: Attempt compilation of FineController**

```bash
mvn compile -pl . -q 2>&1 | grep -i "Fine"
```

Expected: No errors related to Fine

- [ ] **Step 2: If compilation passes, commit**

```bash
git add src/main/java/com/evolutech/core/fleet/API/FineController.java
git commit -m "fix: FineController compiles with generated FinesApi"
```

---

### Task 8: Verify ServiceOrderController compiles

**Covers:** [S6, S7]

**Files:**
- No changes needed — `ServiceOrderController.java` already implements `ServiceOrdersApi`

**Interfaces:**
- Consumes: `ServiceOrdersApi` (generated), `ServiceOrderDTO`, `ServiceOrderPageDTO`, `ServiceOrderRequestDTO`, `ServiceOrderApprovalDTO` (generated)

- [ ] **Step 1: Attempt compilation of ServiceOrderController**

```bash
mvn compile -pl . -q 2>&1 | grep -i "ServiceOrder"
```

Expected: No errors related to ServiceOrder

- [ ] **Step 2: If compilation passes, commit**

```bash
git add src/main/java/com/evolutech/core/fleet/API/ServiceOrderController.java
git commit -m "fix: ServiceOrderController compiles with generated ServiceOrdersApi"
```

---

### Task 9: Verify ApiMapper compiles

**Covers:** [S7]

**Files:**
- No changes needed — `ApiMapper.java` references classes that now exist

**Interfaces:**
- Consumes: All generated DTOs

- [ ] **Step 1: Attempt compilation of ApiMapper**

```bash
mvn compile -pl . -q 2>&1 | grep -i "ApiMapper"
```

Expected: No errors

- [ ] **Step 2: If compilation passes, commit**

```bash
git add src/main/java/com/evolutech/core/fleet/mapper/ApiMapper.java
git commit -m "fix: ApiMapper compiles with all generated DTOs"
```

---

### Task 10: Refactor DriverController to implement DriversApi

**Covers:** [S6, S7]

**Files:**
- Modify: `src/main/java/com/evolutech/core/fleet/API/DriverController.java`
- Modify: `src/main/java/com/evolutech/core/fleet/mapper/ApiMapper.java`

**Interfaces:**
- Consumes: `DriversApi` (generated), `DriverDTO`, `DriverPageDTO`, `DriverRequestDTO` (generated)
- Produces: Updated `DriverController` implementing `DriversApi`, new mapper methods

- [ ] **Step 1: Add mapper methods to ApiMapper**

Add to `ApiMapper.java`:

```java
public com.evolutech.core.fleet.model.dto.request.DriverRequestDTO toDriverRequestInternal(com.evolutech.fleet.api.model.DriverRequestDTO api) {
    return com.evolutech.core.fleet.model.dto.request.DriverRequestDTO.builder()
            .name(api.getName())
            .cpf(api.getCpf())
            .cnhNumber(api.getCnhNumber())
            .cnhCategory(api.getCnhCategory() != null ? api.getCnhCategory().getValue() : null)
            .cnhExpiryDate(api.getCnhExpiryDate())
            .phone(api.getPhone())
            .email(api.getEmail())
            .birthDate(api.getBirthDate())
            .address(api.getAddress())
            .build();
}

public com.evolutech.fleet.api.model.DriverDTO toDriverApi(com.evolutech.core.fleet.model.dto.response.DriverResponseDTO internal) {
    var dto = new com.evolutech.fleet.api.model.DriverDTO();
    dto.setId(internal.getId() != null ? java.util.UUID.fromString(internal.getId()) : null);
    dto.setName(internal.getName());
    dto.setCpf(internal.getCpf());
    dto.setCnhNumber(internal.getCnhNumber());
    dto.setCnhCategory(internal.getCnhCategory() != null ? com.evolutech.fleet.api.model.DriverDTO.CnhCategoryEnum.fromValue(internal.getCnhCategory()) : null);
    dto.setCnhExpiryDate(internal.getCnhExpiryDate());
    dto.setCnhStatus(internal.getCnhStatus() != null ? com.evolutech.fleet.api.model.DriverDTO.CnhStatusEnum.fromValue(internal.getCnhStatus()) : null);
    dto.setPhone(internal.getPhone());
    dto.setEmail(internal.getEmail());
    dto.setBirthDate(internal.getBirthDate());
    dto.setAddress(internal.getAddress());
    dto.setStatus(internal.getStatus() != null ? com.evolutech.fleet.api.model.DriverDTO.StatusEnum.fromValue(internal.getStatus()) : null);
    dto.setCreatedAt(internal.getCreatedAt());
    dto.setUpdatedAt(internal.getUpdatedAt());
    return dto;
}

public com.evolutech.fleet.api.model.DriverPageDTO toDriverPageApi(org.springframework.data.domain.Page<com.evolutech.core.fleet.model.dto.response.DriverResponseDTO> page) {
    var dto = new com.evolutech.fleet.api.model.DriverPageDTO();
    dto.setContent(page.getContent().stream().map(this::toDriverApi).collect(Collectors.toList()));
    dto.setTotalElements((int) page.getTotalElements());
    dto.setTotalPages(page.getTotalPages());
    dto.setSize(page.getSize());
    dto.setNumber(page.getNumber());
    return dto;
}
```

- [ ] **Step 2: Refactor DriverController**

Replace the content of `DriverController.java`:

```java
package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.service.DriverService;
import com.evolutech.fleet.api.DriversApi;
import com.evolutech.fleet.api.model.DriverDTO;
import com.evolutech.fleet.api.model.DriverPageDTO;
import com.evolutech.fleet.api.model.DriverRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DriverController implements DriversApi {

    private final DriverService driverService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<DriverDTO> createDriver(DriverRequestDTO driverRequestDTO) {
        log.info("Creating driver with CPF: {}", driverRequestDTO.getCpf());
        var internalRequest = apiMapper.toDriverRequestInternal(driverRequestDTO);
        var result = driverService.save(internalRequest);
        return ResponseEntity.status(201).body(apiMapper.toDriverApi(result));
    }

    @Override
    public ResponseEntity<Void> deleteDriver(UUID id) {
        log.info("Deleting driver: {}", id);
        driverService.delete(id.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<DriverPageDTO> getAllDrivers(String name, String cpf, String status, String cnhStatus, Integer page, Integer size) {
        log.info("Fetching drivers - page: {}, size: {}", page, size);
        var pageable = PageRequest.of(page, size);
        var drivers = driverService.findByFilters(name, cpf,
                status != null ? com.evolutech.core.fleet.model.utils.enums.DriverStatus.valueOf(status) : null,
                cnhStatus != null ? com.evolutech.core.fleet.model.utils.enums.DriverLicenseStatus.valueOf(cnhStatus) : null,
                pageable);
        return ResponseEntity.ok(apiMapper.toDriverPageApi(drivers));
    }

    @Override
    public ResponseEntity<DriverDTO> getDriverById(UUID id) {
        log.info("Fetching driver: {}", id);
        return driverService.findById(id.toString())
                .map(d -> ResponseEntity.ok(apiMapper.toDriverApi(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<DriverDTO> getDriverByCpf(String cpf) {
        log.info("Fetching driver by CPF: {}", cpf);
        return driverService.findByCpf(cpf)
                .map(d -> ResponseEntity.ok(apiMapper.toDriverApi(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<DriverDTO>> getExpiringCnhs(Integer days) {
        log.info("Fetching drivers with CNH expiring in {} days", days);
        var result = driverService.findExpiringCnhs(days != null ? days : 30);
        return ResponseEntity.ok(result.stream().map(apiMapper::toDriverApi).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<DriverDTO> updateDriver(UUID id, DriverRequestDTO driverRequestDTO) {
        log.info("Updating driver: {}", id);
        var internalRequest = apiMapper.toDriverRequestInternal(driverRequestDTO);
        var result = driverService.update(id.toString(), internalRequest);
        return ResponseEntity.ok(apiMapper.toDriverApi(result));
    }

    @Override
    public ResponseEntity<DriverDTO> updateDriverStatus(UUID id, Object statusRequest) {
        log.info("Updating status for driver: {}", id);
        var statusMap = (java.util.Map<String, String>) statusRequest;
        var status = com.evolutech.core.fleet.model.utils.enums.DriverStatus.valueOf(statusMap.get("status"));
        var result = driverService.updateStatus(id.toString(), status);
        return ResponseEntity.ok(apiMapper.toDriverApi(result));
    }
}
```

- [ ] **Step 3: Compile and verify**

```bash
mvn compile -pl . -q
```

Expected: BUILD SUCCESS

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/evolutech/core/fleet/API/DriverController.java src/main/java/com/evolutech/core/fleet/mapper/ApiMapper.java
git commit -m "refactor: DriverController implements DriversApi via codegen"
```

---

### Task 11: Refactor TripController to implement TripsApi

**Covers:** [S6, S7]

**Files:**
- Modify: `src/main/java/com/evolutech/core/fleet/API/TripController.java`
- Modify: `src/main/java/com/evolutech/core/fleet/mapper/ApiMapper.java`

**Interfaces:**
- Consumes: `TripsApi` (generated), `TripDTO`, `TripPageDTO`, `TripRequestDTO` (generated)
- Produces: Updated `TripController` implementing `TripsApi`, new mapper methods

- [ ] **Step 1: Add mapper methods to ApiMapper**

Add methods for Trip mapping (similar pattern to existing methods). Check `TripResponseDTO` and `TripRequestDTO` internal classes for field names.

- [ ] **Step 2: Refactor TripController**

Remove `@RequestMapping("/trips")`, implement `TripsApi`, convert all methods to `@Override` with generated types.

- [ ] **Step 3: Compile and verify**

```bash
mvn compile -pl . -q
```

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/evolutech/core/fleet/API/TripController.java src/main/java/com/evolutech/core/fleet/mapper/ApiMapper.java
git commit -m "refactor: TripController implements TripsApi via codegen"
```

---

### Task 12: Refactor VehicleAssignmentController to implement VehicleAssignmentsApi

**Covers:** [S6, S7]

**Files:**
- Modify: `src/main/java/com/evolutech/core/fleet/API/VehicleAssignmentController.java`
- Modify: `src/main/java/com/evolutech/core/fleet/mapper/ApiMapper.java`

**Interfaces:**
- Consumes: `VehicleAssignmentsApi` (generated), `VehicleAssignmentDTO`, `VehicleAssignmentPageDTO`, `VehicleAssignmentRequestDTO` (generated)
- Produces: Updated `VehicleAssignmentController` implementing `VehicleAssignmentsApi`, new mapper methods

- [ ] **Step 1: Add mapper methods to ApiMapper**

Add methods for VehicleAssignment mapping.

- [ ] **Step 2: Refactor VehicleAssignmentController**

Remove `@RequestMapping("/assignments")`, implement `VehicleAssignmentsApi`, convert all methods.

- [ ] **Step 3: Compile and verify**

```bash
mvn compile -pl . -q
```

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/evolutech/core/fleet/API/VehicleAssignmentController.java src/main/java/com/evolutech/core/fleet/mapper/ApiMapper.java
git commit -m "refactor: VehicleAssignmentController implements VehicleAssignmentsApi via codegen"
```

---

### Task 13: Full compilation verification

**Covers:** [S1, S6, S7]

**Files:** None

- [ ] **Step 1: Clean compile**

```bash
mvn clean compile -q
```

Expected: BUILD SUCCESS with no errors

- [ ] **Step 2: Verify all controllers compile**

Check that no `cannot find symbol` errors remain for any of the generated classes.

- [ ] **Step 3: Final commit if any remaining fixes**

```bash
git add -A
git commit -m "fix: resolve all swagger codegen compilation errors"
```
