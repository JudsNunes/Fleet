---
name: Nova User Story
about: Criar uma nova história de usuário no backlog
title: '[US] '
labels: 'user-story'
assignees: ''
---

**Épico:** [Ex: JFE-EPIC-XX]
**Feature:** [Ex: JFE-FEAT-XXX]
**Story Points:** [1, 2, 3, 5, 8, 13]

### 📖 História
**Como** [persona], 
**eu quero** [ação] 
**para** [benefício].

### ✅ Critérios de Aceite (Gherkin)

```gherkin
Cenário: [Nome]
  Dado que [condição]
  Quando [ação]
  Então [resultado]

  ---

### 3. As User Stories Iniciais (Prontas para suas Issues)

Aqui estão as primeiras User Stories (Épicos 0 e 1) já no formato markdown perfeito para você abrir no GitHub. Basta copiar o bloco e colar como uma nova Issue!

#### Issue 1: JFE-US-001
**Título da Issue:** `[JFE-US-001] Ambiente de banco reproduzível`
```markdown
**Épico:** JFE-EPIC-00 - Fundação e Qualidade
**Feature:** JFE-FEAT-001 - Estabilização do Monolito MVP
**Story Points:** 3

### 📖 História
**Como** desenvolvedor, 
**eu quero** que o banco PostgreSQL esteja configurado de forma consistente 
**para** subir o projeto localmente sem erros de conexão ou schema.

### ✅ Critérios de Aceite

```gherkin
Cenário: Aplicação conecta ao PostgreSQL via Docker Compose
  Dado que o Docker Compose está rodando com o serviço postgres
  E o application.properties aponta para jdbc:postgresql://localhost:5432/fleet_db
  Quando eu inicio a aplicação Spring Boot
  Então a aplicação sobe sem erro de conexão
  E as tabelas são criadas via Flyway migration V1

Cenário: Configuração MySQL não é mais suportada
  Dado que o application.properties contém jdbc:mysql
  Quando eu inicio a aplicação
  Então a aplicação falha com mensagem clara de driver ausente

  #### Issue 2: JFE-US-002
**Título da Issue:** `[JFE-US-002] Identificadores consistentes no domínio`
```markdown
**Épico:** JFE-EPIC-00 - Fundação e Qualidade
**Feature:** JFE-FEAT-001 - Estabilização do Monolito MVP
**Story Points:** 5

### 📖 História
**Como** desenvolvedor, 
**eu quero** IDs padronizados em todas as entidades 
**para** evitar erros de persistência e inconsistência na API.

### ✅ Critérios de Aceite

```gherkin
Cenário: Veículo criado recebe UUID válido
  Dado que não existe veículo com placa "ABC1D23"
  Quando eu envio POST /api/v1/vehicles com placa "ABC1D23"
  Então recebo HTTP 201
  E o campo id é um UUID válido no formato string
  E o id retornado é persistido corretamente no banco

Cenário: CRUD de manutenção usa mesmo tipo de ID em todas as camadas
  Dado que existe um veículo com id "550e8400-e29b-41d4-a716-446655440000"
  Quando eu crio uma manutenção vinculada a esse veículo
  Então o id da manutenção é UUID string
  E GET /api/v1/maintenances/{id} retorna o mesmo registro

#### Issue 3: JFE-US-003
**Título da Issue:** `[JFE-US-003] Tratamento de erros padronizado na API`
```markdown
**Épico:** JFE-EPIC-00 - Fundação e Qualidade
**Feature:** JFE-FEAT-001 - Estabilização do Monolito MVP
**Story Points:** 3

### 📖 História
**Como** consumidor da API, 
**eu quero** respostas HTTP semânticas e mensagens claras 
**para** integrar sem adivinhar o tipo de erro.

### ✅ Critérios de Aceite
```gherkin
Cenário: Recurso não encontrado retorna 404
  Dado que não existe veículo com id "00000000-0000-0000-0000-000000000000"
  Quando eu envio GET /api/v1/vehicles/00000000-0000-0000-0000-000000000000
  Então recebo HTTP 404
  E o corpo contém code "VEHICLE_NOT_FOUND" e message descritiva

Cenário: Placa duplicada retorna 409
  Dado que existe veículo com placa "ABC1D23"
  Quando eu envio POST /api/v1/vehicles com placa "ABC1D23"
  Então recebo HTTP 409
  E o corpo contém code "VEHICLE_PLATE_CONFLICT"