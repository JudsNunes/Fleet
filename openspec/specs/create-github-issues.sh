# GitHub Issues - Próximo Ciclo

Execute após autenticar `gh auth login`:

```bash
# Issue 1: Gestão de Motoristas
gh issue create --repo JudsNunes/Fleet \
  --title "Feat: Gestão de Motoristas (JFE-FEAT-008)" \
  --label "enhancement,priority/high" \
  --body "## Resumo
CRUD completo de motoristas com gestão de CNH.

## Escopo
- Entity DriverEntity com name, cpf, cnhNumber, cnhCategory, cnhExpiryDate, cnhStatus
- Enums: DriverStatus (ACTIVE/INACTIVE/SUSPENDED), DriverLicenseCategory
- Validações: CPF único, CNH única, CNH vencida → status EXPIRED
- Endpoints: CRUD + /cpf/{cpf} + /expiring-cnhs
- Migration V7

## Spec
Veja \`openspec/specs/tasks-driver-management.md\`

## Estimativa: ~10h"

# Issue 2: Gestão de Viagens
gh issue create --repo JudsNunes/Fleet \
  --title "Feat: Gestão de Viagens (JFE-FEAT-007)" \
  --label "enhancement,priority/high" \
  --body "## Resumo
Gestão completa de viagens com workflow de status e detecção de desvio de rota.

## Escopo
- Entity TripEntity com vehicle, driver, origin, destination, mileage, status
- Workflow: PLANNED → IN_PROGRESS → COMPLETED
- Detecção automática de desvio de rota (>15% da distância planejada)
- Atualização automática do mileage do veículo ao completar
- Endpoints: CRUD + /start, /complete, /cancel, /deviation
- Migration V8

## Dependências
- JFE-FEAT-008 (Motoristas) deve ser implementado primeiro

## Spec
Veja \`openspec/specs/tasks-trip-management.md\`

## Estimativa: ~16h"

# Issue 3: Assignment de Veículos
gh issue create --repo JudsNunes/Fleet \
  --title "Feat: Assignment de Veículos (JFE-FEAT-009)" \
  --label "enhancement,priority/high" \
  --body "## Resumo
Vinculação motorista-veículo com controle de sobreposição.

## Escopo
- Entity VehicleAssignmentEntity com vehicle, driver, startDate, endDate, status
- Validações: sem sobreposição de atribuições ativas, vehicle/driver devem estar ACTIVE
- Endpoints: CRUD + /end, filtros por veículo/motorista/ativas
- Migration V9

## Dependências
- JFE-FEAT-008 (Motoristas) deve ser implementado primeiro

## Spec
Veja \`openspec/specs/tasks-vehicle-assignment.md\`

## Estimativa: ~10h"

# Issue 4: Atualizar Spec JFE-FEAT-014
gh issue create --repo JudsNunes/Fleet \
  --title "Spec: Atualizar JFE-FEAT-014 com status de implementação" \
  --label "documentation" \
  --body "## Resumo
Atualizar a spec JFE-FEAT-014 para refletir que §1-3 e §5-6 já foram implementados nas branches feature.

## Branches implementadas
- Feat/vehicle-technical-specs (§2)
- Feat/service-order-cost-allocation (§5 + §6.1)
- Feat/audit-log (§6.3)
- Feat/maintenance-alert (§1)
- Feat/fuel-validation-consumption (§3)
- Feat/traffic-fines (§6.2)
- Feat/contract-first-api (OpenAPI Generator)

## Pendente
- §4 Monitoramento de Rota (depende de JFE-FEAT-007)"
```
