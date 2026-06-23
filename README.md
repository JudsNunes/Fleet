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
- **API**: REST com OpenAPI 3.0
- **Documentação**: SpringDoc OpenAPI (Swagger UI)
- **Mapeamento**: MapStruct
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
| GET | `/api/v1/vehicles` | Listar veículos (paginado) |
| GET | `/api/v1/vehicles/{id}` | Buscar veículo por UUID |
| POST | `/api/v1/vehicles` | Criar veículo |
| PUT | `/api/v1/vehicles/{id}` | Atualizar veículo |
| PATCH | `/api/v1/vehicles/{id}/status` | Atualizar status |
| DELETE | `/api/v1/vehicles/{id}` | Soft-delete |
| GET | `/api/v1/vehicles/search?plate=X&brand=Y&status=Z` | Busca avançada |

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

## Modelos de Dados

### Vehicle

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
  "maintenanceDate": "2024-01-15"
}
```

## Formato de Placa

O sistema aceita dois formatos de placa:

- **Mercosul**: `ABC1D23` (letra, letra, letra, número, letra, número, número)
- **Antigo**: `ABC-1234` (letra, letra, letra, hífen, número, número, número, número)

## Status do Veículo

- `ACTIVE` - Veículo ativo
- `INACTIVE` - Veículo inativo
- `MAINTENANCE` - Em manutenção
- `DECOMMISSIONED` - Desativado

## Status da Manutenção

- `PENDING` - Pendente
- `IN_PROGRESS` - Em andamento
- `COMPLETED` - Concluída
- `OVERDUE` - Vencida

## Desenvolvimento

### Estrutura do Projeto

```
src/main/java/com/evolutech/core/fleet/
├── API/                    # Controllers REST
├── exception/              # Exceções customizadas
├── infra/                  # Configurações e handlers
├── mapper/                 # MapStruct mappers
├── model/
│   ├── dto/                # Data Transfer Objects
│   ├── entity/             # Entidades JPA
│   └── utils/enums/        # Enums de domínio
├── repository/             # Repositórios JPA
└── service/                # Lógica de negócio
    └── impl/               # Implementações
```

### Testes

```bash
mvn test
```
