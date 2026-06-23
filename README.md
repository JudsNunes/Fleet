# Fleet Management API - BFF

Backend for Frontend (BFF) para o sistema de gestão de frota veicular da Evolutech.

## Arquitetura

```
[Web Admin React] → [fleet-bff] → [MySQL]
[Mobile React Native] →
```

O Fleet BFF é um serviço Spring Boot que serve como ponto de entrada único para os frontends (web-admin e mobile-driver), agregando dados dos serviços de domínio.

## Stack Tecnológica

- **Backend**: Java 17, Spring Boot 4.0.2
- **Banco**: MySQL 8.0
- **Migrations**: Flyway
- **API**: REST com OpenAPI 3.0 (Contract-First via OpenAPI Generator)
- **Documentação**: SpringDoc OpenAPI (Swagger UI)
- **Mapeamento**: MapStruct + ApiMapper (conversão DTOs API/Internos)
- **Validação**: Jakarta Validation + Hibernate Validator

## Pré-requisitos

- Java 17+
- Docker e Docker Compose
- Maven 3.8+

## Setup

### 1. Iniciar banco de dados

```bash
docker compose up -d
```

O MySQL estará disponível em `localhost:3306`.

### 2. Compilar e executar

```bash
mvn clean spring-boot:run
```

### 3. Acessar a aplicação

- **API**: http://localhost:8080/api/v1
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/api/v1/actuator/health

## Endpoints

### Vehicles

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/vehicles` | Listar veículos (paginado, filtros) |
| GET | `/api/v1/vehicles/{id}` | Buscar veículo por UUID |
| POST | `/api/v1/vehicles` | Criar veículo |
| PUT | `/api/v1/vehicles/{id}` | Atualizar veículo |
| PATCH | `/api/v1/vehicles/{id}/status` | Atualizar status |
| DELETE | `/api/v1/vehicles/{id}` | Soft-delete |

### Maintenances

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/maintenances` | Listar manutenções (paginado) |
| GET | `/api/v1/maintenances/{id}` | Buscar manutenção por UUID |
| POST | `/api/v1/maintenances` | Criar manutenção |
| PUT | `/api/v1/maintenances/{id}` | Atualizar manutenção |
| DELETE | `/api/v1/maintenances/{id}` | Soft-delete |
| GET | `/api/v1/maintenances/vehicle/{vehicleId}` | Manutenções por veículo |
| GET | `/api/v1/maintenances/status/{status}` | Manutenções por status |

### Service Orders

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/service-orders` | Listar ordens de serviço |
| GET | `/api/v1/service-orders/{id}` | Buscar OS por UUID |
| POST | `/api/v1/service-orders` | Criar OS |
| PUT | `/api/v1/service-orders/{id}` | Atualizar OS |
| DELETE | `/api/v1/service-orders/{id}` | Soft-delete |
| PATCH | `/api/v1/service-orders/{id}/approve` | Aprovar OS |
| PATCH | `/api/v1/service-orders/{id}/reject` | Rejeitar OS |
| PATCH | `/api/v1/service-orders/{id}/complete` | Completar OS |
| GET | `/api/v1/service-orders/vehicle/{vehicleId}` | OS por veículo |
| GET | `/api/v1/service-orders/status/{status}` | OS por status |
| GET | `/api/v1/service-orders/warranty/{vehicleId}` | Garantias ativas |

### Audit Logs

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/audit-logs` | Listar logs de auditoria |
| GET | `/api/v1/audit-logs/entity/{entityType}/{entityId}` | Logs por entidade |
| GET | `/api/v1/audit-logs/entity/{entityType}` | Logs por tipo de entidade |
| GET | `/api/v1/audit-logs/user/{userId}` | Logs por usuário |

### Fines (Multas)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/fines` | Listar multas |
| GET | `/api/v1/fines/{id}` | Buscar multa por UUID |
| POST | `/api/v1/fines` | Criar multa |
| PUT | `/api/v1/fines/{id}` | Atualizar multa |
| DELETE | `/api/v1/fines/{id}` | Deletar multa |
| GET | `/api/v1/fines/driver/{driverCpf}` | Multas por CPF |
| GET | `/api/v1/fines/vehicle/{vehicleId}` | Multas por veículo |
| GET | `/api/v1/fines/status/{status}` | Multas por status |

## Modelos de Dados

### Vehicle (com Ficha Técnica)

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "plate": "ABC1D23",
  "model": "Corolla",
  "brand": "Toyota",
  "year": 2023,
  "color": "Preto",
  "mileage": 5000.0,
  "status": "ACTIVE",
  "chassis": "9BWZZZ377VE000001",
  "renavam": "12345678901",
  "fuelType": "FLEX",
  "cargoCapacityKg": 500.0,
  "passengerCapacity": 5,
  "engineType": "2.0",
  "createdAt": "2024-01-15T10:00:00",
  "updatedAt": "2024-01-15T10:00:00"
}
```

### Maintenance

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "vehicleId": "550e8400-e29b-41d4-a716-446655440000",
  "description": "Troca de óleo",
  "type": "MAINTENANCE",
  "cost": 150.00,
  "mileage": 50000.0,
  "nextMileage": 60000.0,
  "status": "PENDING",
  "maintenanceDate": "2024-01-15",
  "serviceOrderId": "uuid-da-os",
  "costCenterId": "CC-001",
  "projectId": "PRJ-001"
}
```

### Service Order

```json
{
  "id": "uuid",
  "vehicleId": "uuid-do-veiculo",
  "description": "Troca de pneus",
  "status": "APPROVED",
  "approvedBy": "admin@evolutech.com",
  "approvedAt": "2024-01-15T10:00:00",
  "warrantyExpiryDate": "2025-01-15T00:00:00"
}
```

### Fine (Multa)

```json
{
  "id": "uuid",
  "vehicleId": "uuid-do-veiculo",
  "driverCpf": "123.456.789-00",
  "description": "Excesso de velocidade",
  "amount": 293.47,
  "infractionDate": "2024-01-15",
  "points": 7,
  "status": "PENDING",
  "costCenterId": "CC-001"
}
```

## Regras de Negócio

### Combustível
- Veículos GASOLINE só aceitam GASOLINE
- Veículos DIESEL só aceitam DIESEL
- Veículos FLEX aceitam GASOLINE ou ETHANOL
- Veículos ELECTRIC só aceitam ELECTRIC
- Consumo anômalo (>20% acima da média) gera flag

### Ordem de Serviço
- Maintenance type=MAINTENANCE requer OS vinculada
- OS deve estar com status APPROVED para criar manutenção
- Garantia verificada antes de autorizar novo pagamento

### Rateio de Custos
- FUEL, TOLL, PARKING: costCenterId ou projectId obrigatório
- MAINTENANCE: obrigatório via OS

### Auditoria
- Logs automáticos para CREATE, UPDATE, DELETE, STATUS_CHANGE
- Campos críticos: status, deletedAt, maintenanceStatus

## Enums

### FuelType
`GASOLINE`, `DIESEL`, `ETHANOL`, `FLEX`, `ELECTRIC`, `HYBRID`

### VehicleStatus
`ACTIVE`, `INACTIVE`, `MAINTENANCE`, `DECOMMISSIONED`

### MaintenanceStatus
`PENDING`, `IN_PROGRESS`, `COMPLETED`, `OVERDUE`

### MaintenanceType
`MAINTENANCE`, `FUEL`, `TOLL`, `PARKING`, `OTHER`

### ServiceOrderStatus
`PENDING`, `APPROVED`, `REJECTED`, `COMPLETED`

### FineStatus
`PENDING`, `PAID`, `DISPUTED`

### AuditAction
`CREATE`, `UPDATE`, `DELETE`, `STATUS_CHANGE`

## Estrutura do Projeto

```
src/main/java/com/evolutech/core/fleet/
├── API/                        # Controllers (implementam interfaces OpenAPI)
├── exception/                  # Exceções customizadas
├── infra/                      # Configurações, handlers, scheduler, listeners
├── mapper/                     # MapStruct mappers + ApiMapper
├── model/
│   ├── dto/
│   │   ├── request/            # DTOs de entrada
│   │   └── response/           # DTOs de saída
│   ├── entity/                 # Entidades JPA
│   └── utils/enums/            # Enums de domínio
├── repository/                 # Repositórios JPA
└── service/                    # Lógica de negócio
    └── impl/                   # Implementações

src/main/resources/
├── api/evolutech-fleet.yaml    # OpenAPI Spec (contract-first)
└── db/migration/               # Flyway migrations (V1-V6)
```

## Features Futuras

| ID | Feature | Prioridade | Descrição |
|----|---------|------------|-----------|
| JFE-FEAT-007 | Gestão de Viagens | C | CRUD de viagens com tracking de rotas |
| JFE-FEAT-008 | Gestão de Motoristas | C | Cadastro completo de motoristas com CNH |
| JFE-FEAT-009 | Assignment de Veículos | C | Vinculação motorista-veículo com histórico |
| JFE-FEAT-010 | Notificações Push | C | Integração com notification-service via RabbitMQ |
| JFE-FEAT-011 | Integração GPS/Telemetria | W | Rastreamento em tempo real via telemetria |
| JFE-FEAT-012 | Multi-tenancy | W | Suporte a múltiplas empresas/organizações |
| JFE-FEAT-013 | Export de Relatórios | W | Geração de relatórios PDF/Excel via report-service |
| JFE-FEAT-014-01 | Configuração de Intervalos | S | Interface para configurar intervalos de manutenção por veículo |
| JFE-FEAT-014-02 | Monitoramento de Rota | C | Registro de desvio de rota (>15% do planejado) — depende de Gestão de Viagens |
| JFE-FEAT-014-03 | Dashboard de Alertas | S | Visualização de alertas de manutenção preventiva ativos |
| JFE-FEAT-014-04 | Relatório de Consumo | S | Análise de consumo de combustível por veículo/período |

## Testes

```bash
mvn test
```

## Contrato API (Contract-First)

A API segue o padrão **Contract-First** com OpenAPI Generator:

1. O contrato é definido em `src/main/resources/api/evolutech-fleet.yaml`
2. Interfaces Java são geradas automaticamente via `openapi-generator-maven-plugin`
3. Controllers implementam as interfaces geradas com `@Override`
4. `ApiMapper` converte entre DTOs gerados e DTOs internos

Para regenerar as interfaces:
```bash
mvn generate-sources
```
