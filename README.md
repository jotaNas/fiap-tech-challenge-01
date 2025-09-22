# User API v1

##  Requisitos atendidos
- CRUD de usuários; busca por nome; e-mail único  
- Endpoint separado para troca de senha (`PATCH /api/v1/users/{{id}}/password`)  
- Endpoint distinto para atualização de dados (`PUT /api/v1/users/{{id}}`)  
- Registro de `lastModifiedAt` automático (via **JPA callbacks**)  
- Validação simples de login (`POST /api/v1/auth/login`) — sem Spring Security  
- Versionamento de API no path (`/api/v1/...`)  
- Erros padronizados usando **ProblemDetail (RFC 7807)** via `@RestControllerAdvice`  
- Documentação Swagger: `/api/v1/swagger` (OpenAPI em `/api/v1/docs`)  
- Docker Compose com **PostgreSQL**  

---

##  Rodando local com Docker
```bash
docker compose up --build
```

### Swagger disponível em:
http://localhost:8080/api/v1/swagger

## Rodando local sem Docker
Suba um PostgreSQL local (DB: userdb, user: postgres, pass: postgres) ou ajuste o application.yml.

Execute o projeto:

```bash
./gradlew bootRun
```
## Endpoints principais
POST /api/v1/users

GET /api/v1/users?name=jo

GET /api/v1/users/{{id}}

PUT /api/v1/users/{{id}}

PATCH /api/v1/users/{{id}}/password

DELETE /api/v1/users/{{id}}

POST /api/v1/auth/login

### Tipos de usuário obrigatórios
RESTAURANT_OWNER

CLIENT

### Observações
Senhas armazenadas com BCrypt (spring-security-crypto).

Campos validados com jakarta.validation.

### Postman Collection
O repositório inclui uma coleção Postman exportada:

[Postman Collection](postman-collection.json)

Ela cobre:

Criação de usuário válido e duplicado

Busca por ID e nome

Atualização sem senha

Troca de senha (sucesso e erro)

Exclusão de usuário

Login simples
