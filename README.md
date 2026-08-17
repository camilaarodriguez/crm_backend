# Sistema CRM - API REST

API REST em Spring Boot 3 / Java 17 para gestao de **Usuarios** e **Clientes**, seguindo
arquitetura MVC em camadas (Controller -> Service -> Repository), com DTOs (records),
Lombok, tratamento global de excecoes e banco H2 em memoria (nao precisa configurar nada
para rodar).

## Stack

- Java 17
- Spring Boot 3.3.4 (Web, Data JPA, Validation)
- Lombok
- H2 Database (em memoria)
- Maven

## Estrutura de pacotes

```
com.crmapi.sistemacrm
├── controller          -> Camada de apresentacao (REST controllers)
├── service              -> Interfaces de regra de negocio
│   └── impl              -> Implementacoes dos services
├── repository            -> Interfaces Spring Data JPA
│   └── specification     -> Specifications para filtros dinamicos (busca, role, status, etc.)
├── model                 -> Entidades JPA
│   └── enums              -> Enums de dominio (UsuarioRole, StatusFunil)
├── dto
│   ├── usuario            -> Records de entrada/saida de Usuario
│   └── cliente             -> Records de entrada/saida de Cliente
├── mapper                -> Conversao Entidade <-> DTO
└── exception              -> Excecoes de dominio + @RestControllerAdvice
```

## Como rodar

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`. O console do H2 fica disponivel em
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:crmdb`, usuario `sa`, senha vazia).

## Endpoints

### UsuarioController

| # | Verbo | Rota | Entra | Sai |
|---|-------|------|-------|-----|
| 1 | POST | `/api/usuarios` | `UsuarioCreateDTO` (body) | `201` + `UsuarioResponseDTO` |
| 2 | GET | `/api/usuarios?busca=&role=&ativo=&page=&size=` | `@RequestParam` | `200` + `Page<UsuarioResponseDTO>` |
| 3 | GET | `/api/usuarios/{id}` | `@PathVariable` | `200` + `UsuarioResponseDTO` |
| 4 | PUT | `/api/usuarios/{id}` | `@PathVariable` + `UsuarioUpdateDTO` | `200` + `UsuarioResponseDTO` |
| 5 | PATCH | `/api/usuarios/{id}/status` | `@PathVariable` + `UsuarioStatusDTO` | `200` + `UsuarioResponseDTO` |
| 6 | DELETE | `/api/usuarios/{id}` | `@PathVariable` | `204` |

### ClienteController

| # | Verbo | Rota | Entra | Sai |
|---|-------|------|-------|-----|
| 7 | POST | `/api/clientes` | `ClienteCreateDTO` (body) | `201` + `ClienteResponseDTO` |
| 8 | GET | `/api/clientes?busca=&status=&vendedorId=&incluirInativos=&page=&size=` | `@RequestParam` | `200` + `Page<ClienteResponseDTO>` |
| 9 | GET | `/api/clientes/{id}` | `@PathVariable` | `200` + `ClienteResponseDTO` |
| 10 | PUT | `/api/clientes/{id}` | `@PathVariable` + `ClienteUpdateDTO` | `200` + `ClienteResponseDTO` |
| 11 | PATCH | `/api/clientes/{id}/status-funil` | `@PathVariable` + `ClienteStatusFunilDTO` | `200` + `ClienteResponseDTO` |
| 12 | DELETE | `/api/clientes/{id}` | `@PathVariable` | `204` |

## Exemplos de uso (curl)

```bash
# Criar usuario (vendedor)
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Joao Silva","email":"joao@empresa.com","senha":"123456","role":"VENDEDOR"}'

# Listar usuarios com filtro e paginacao
curl "http://localhost:8080/api/usuarios?busca=joao&ativo=true&page=0&size=10"

# Buscar usuario por id
curl http://localhost:8080/api/usuarios/1

# Atualizar usuario
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Joao Silva","email":"joao@empresa.com","role":"GERENTE"}'

# Ativar/inativar usuario
curl -X PATCH http://localhost:8080/api/usuarios/1/status \
  -H "Content-Type: application/json" \
  -d '{"ativo": false}'

# Remover usuario
curl -X DELETE http://localhost:8080/api/usuarios/1

# Criar cliente (vinculado a um vendedor existente)
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{"nome":"Empresa XPTO","email":"contato@xpto.com","telefone":"11999999999","vendedorId":1,"statusFunil":"NOVO"}'

# Listar clientes com filtros
curl "http://localhost:8080/api/clientes?status=NOVO&vendedorId=1&incluirInativos=false&page=0&size=10"

# Avancar status no funil
curl -X PATCH http://localhost:8080/api/clientes/1/status-funil \
  -H "Content-Type: application/json" \
  -d '{"statusFunil":"EM_CONTATO"}'
```

## Requisitos atendidos

- Pacotes organizados por responsabilidade (nao e projeto DEMO padrao).
- CRUD completo para `Usuario` e `Cliente`.
- 12 endpoints (6 por controller), com verbos HTTP e codigos de status adequados
  (`201`, `200`, `204`, `404`, `400`, `409`).
- Retorno estruturado via `ResponseEntity` + DTOs; erros tratados de forma padronizada
  (`GlobalExceptionHandler` + `ErrorResponseDTO`).
- Uso de `@PathVariable`, `@RequestParam` e `@RequestBody` em rotas distintas.
- Arquitetura MVC em camadas: Controller -> Service -> Repository -> Model.
- Uso de Lombok (`@Data`, `@Builder`, `@RequiredArgsConstructor`) e `record` para DTOs.
- DTOs de entrada e saida separados por operacao (Create, Update, Status, Response).
