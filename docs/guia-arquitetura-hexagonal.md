# Guia de Arquitetura Hexagonal — todo.list Bootcamp

Este documento explica **como este projeto está organizado**, **porquê** cada decisão existe, e **como ensinar** o tema a alunos de bootcamp. O objetivo não é decorar pastas: é entender **direção das dependências** e **separação de responsabilades**.

---

## Índice

1. [Ideia central em 2 minutos](#1-ideia-central-em-2-minutos)
2. [Porquê arquitetura hexagonal](#2-porquê-arquitetura-hexagonal)
3. [As três zonas do projeto](#3-as-três-zonas-do-projeto)
4. [Estrutura de pastas (mapa mental)](#4-estrutura-de-pastas-mapa-mental)
5. [Tema: Domínio](#5-tema-domínio)
6. [Tema: Aplicação (casos de uso)](#6-tema-aplicação-casos-de-uso)
7. [Tema: Portas (contratos)](#7-tema-portas-contratos)
8. [Tema: Adaptadores de entrada (REST)](#8-tema-adaptadores-de-entrada-rest)
9. [Tema: Infraestrutura e repositórios](#9-tema-infraestrutura-e-repositórios)
10. [Tema: Mappers (MapStruct)](#10-tema-mappers-mapstruct)
11. [Tema: Validação (declarativa vs negócio)](#11-tema-validação-declarativa-vs-negócio)
12. [Tema: Exceções — o modelo completo](#12-tema-exceções--o-modelo-completo)
13. [Tema: DTOs e modelos](#13-tema-dtos-e-modelos)
14. [Tema: Persistência e auditoria de datas](#14-tema-persistência-e-auditoria-de-datas)
15. [Fluxo completo: criar um Todo](#15-fluxo-completo-criar-um-todo)
16. [Estratégia para ensinar no bootcamp (por aulas)](#16-estratégia-para-ensinar-no-bootcamp-por-aulas)
17. [Checklist: nova feature do zero](#17-checklist-nova-feature-do-zero)
18. [Erros comuns e como corrigir](#18-erros-comuns-e-como-corrigir)
19. [Glossário rápido](#19-glossário-rápido)

---

## 1. Ideia central em 2 minutos

Imagine o sistema como um **hexágono**:

- **No centro** ficam as regras de negócio estáveis (o que o produto *é*).
- **Na periferia** ficam os detalhes técnicos que mudam (HTTP, H2, JPA, JSON, etc.).
- O centro **não conhece** a periferia. A periferia **implementa contratos** definidos pelo centro.

Em código, isso significa:

| Camada | Pergunta que responde |
|--------|------------------------|
| **Domínio** | O que é um User? Um Todo? Que erros de negócio existem? |
| **Aplicação** | O que o sistema *faz*? (criar user, listar todos, etc.) |
| **Adaptadores** | Como o mundo externo *fala* com o sistema? (REST, JSON) |
| **Infraestrutura** | Como *guardamos* dados? (JPA, H2, entidades) |

**Regra de ouro:** dependências apontam **para dentro**.  
`Controller` → `Service` → `Port` ← `RepositoryAdapter` → `JpaRepository`

O domínio **nunca** importa Spring Web, JPA nem DTOs de API.

---

## 2. Porquê arquitetura hexagonal

### Problema do projeto “em camadas técnicas”

```
controller/
service/
repository/
```

Tudo misturado por **tipo técnico**. Com o tempo:

- Regras de negócio espalham-se em controllers e repositories.
- Trocar H2 por PostgreSQL ou REST por fila obriga a reescrever lógica.
- Testes ficam lentos porque tudo depende de framework.

### O que ganhamos com hexagonal (neste repo)

| Benefício | Exemplo neste projeto |
|-----------|------------------------|
| **Testar negócio sem HTTP/BD** | `UserService` testável com repositório fake |
| **Trocar detalhes técnicos** | Trocar JPA mantendo `UserRepositoryOutPort` |
| **Onboarding por contexto** | Pasta `features/user`, `features/todo` |
| **Erros previsíveis** | Exceções de domínio + mappers HTTP consistentes |
| **API estável** | DTOs na aplicação; entidades JPA isoladas |

---

## 3. As três zonas do projeto

```
src/main/java/com/richard/todo/
├── Application.java              # Bootstrap Spring
├── root/                         # Transversal (configs, shared)
│   ├── configs/
│   └── shared/                   # ErrorResponseDTO, etc.
└── features/
    ├── user/                     # Contexto User
    └── todo/                     # Contexto Todo
```

Cada **feature** repete o mesmo “molde”:

```
features/<nome>/
├── domain/           # Núcleo de negócio
├── application/    # Casos de uso + portas + DTOs
├── adapters/         # REST, exception mappers
└── infrastructure/   # JPA, entidades, repositórios
```

**Porquê por feature e não por tipo global?**  
Porque o negócio é organizado por **contexto** (User, Todo), não por “todos os controllers do mundo”.

---

## 4. Estrutura de pastas (mapa mental)

### Feature `user`

| Pasta | Responsabilidade |
|-------|------------------|
| `domain/models` | `UserModel` — conceito de negócio |
| `domain/exceptions` | Erros de negócio do user |
| `domain/enums` | `UserErrorCode` |
| `domain/mappers` | MapStruct: Entity ↔ Model ↔ ResponseDTO |
| `application/dtos` | Request/Response da API |
| `application/ports/in` | `UserServiceInPort` — o que a feature oferece |
| `application/ports/out` | `UserRepositoryOutPort` — o que precisa do exterior |
| `application/services` | `UserService` — orquestração |
| `adapters/rest/controllers` | `UserController` |
| `adapters/rest/exceptions` | Exception mappers HTTP |
| `infrastructure/entity` | `UserEntity` (JPA) |
| `infrastructure/repository` | `UserJpaRepository` + `UserRepositoryAdapter` |

### Feature `todo`

Mesma lógica, com relação `Todo` → `User`.

---

## 5. Tema: Domínio

### O que entra aqui

- **Models** (`UserModel`, `TodoModel`): objetos de negócio, sem anotações JPA.
- **Exceptions** (`UserNotFoundException`, `TodoNotFoundException`, …).
- **Enums de erro** (`UserErrorCode`, `TodoErrorCode`).
- **Enums de negócio** (`TodoStatusEnum`, `TodoPriorityEnum`).

### O que NÃO entra aqui

- `@Entity`, `@Table`, `JpaRepository`
- `@RestController`, `@RequestBody`
- SQL, JSON, status HTTP

### Porquê

O domínio deve sobreviver se amanhã:

- a API passar de REST para gRPC;
- a BD mudar de H2 para PostgreSQL;
- o framework mudar.

**Analogia para bootcamp:** o domínio é o “manual de regras do jogo”. O resto são “tabuleiros diferentes” (web, BD).

---

## 6. Tema: Aplicação (casos de uso)

### Responsabilidade

A camada de aplicação **orquestra**:

1. Recebe pedido (via port de entrada / service).
2. Aplica **regras de negócio** que não cabem numa anotação.
3. Chama **portas de saída** (repositório).
4. Devolve DTOs de resposta.

### Exemplo: `UserService.create`

**Porquê o serviço valida email duplicado aqui?**  
Porque “email já existe” é **regra de negócio**, não formato de campo. Isso não se resolve com `@NotBlank`.

**Porquê não aceder ao JPA no serviço?**  
Porque o serviço depende de `UserRepositoryOutPort` (contrato), não de `UserJpaRepository` (detalhe).

### O que fica FORA do serviço (de propósito)

| Responsabilidade | Onde fica |
|------------------|-----------|
| Traduzir HTTP → DTO | Controller |
| Validar formato (@NotBlank) | DTO + `@Valid` |
| Traduzir exceção → JSON/HTTP | Exception Mapper |
| SQL / INSERT | JPA + Entity |
| Preencher `created_at` | Entity + `@CreatedDate` |

---

## 7. Tema: Portas (contratos)

### Porta de entrada (`*ServiceInPort`)

Define **o que a feature expõe** ao exterior:

```java
UserResponseDTO create(CreateUserRequestDTO request);
UserResponseDTO findById(UUID id);
// ...
```

**Porquê interface?**  
O controller depende do contrato, não da implementação. Facilita testes e troca de implementação.

### Porta de saída (`*RepositoryOutPort`)

Define **o que a aplicação precisa do mundo externo** para persistir:

```java
UserModel save(UserModel user);
Optional<UserModel> findById(UUID id);
// ...
```

**Porquê não usar `JpaRepository` direto no serviço?**  
Porque `JpaRepository` é detalhe de infraestrutura. O serviço fala em `UserModel`, não em `UserEntity`.

### Direção da dependência (desenho para quadro branco)

```
[Controller] ──► [UserService] ──► [UserRepositoryOutPort]
                                              ▲
                                              │ implements
                                    [UserRepositoryAdapter]
                                              │
                                              ▼
                                    [UserJpaRepository]
```

---

## 8. Tema: Adaptadores de entrada (REST)

### Controller = tradutor de protocolo

`UserController` / `TodoController`:

- Mapeiam URLs e verbos HTTP.
- Recebem JSON → DTO.
- Chamam `*ServiceInPort`.
- Devolvem DTO (Spring serializa para JSON).

**Porquê controllers “finos”?**  
Porque HTTP é um **detalhe**. Amanhã pode existir um adaptador CLI ou mensageria que chama o **mesmo** `UserServiceInPort`.

### `@Valid` no body

```java
public UserResponseDTO create(@Valid @RequestBody CreateUserRequestDTO request)
```

**Porquê no controller e não no serviço?**  
Validação de **formato** (campo vazio, email inválido) é contrato da **entrada HTTP**. O serviço assume DTO já válido em estrutura; trata **negócio**.

---

## 9. Tema: Infraestrutura e repositórios

### Estratégia deste projeto: repositório “burro”

| Componente | Função |
|------------|--------|
| `UserJpaRepository` | Spring Data — queries JPA |
| `UserRepositoryAdapter` | Implementa `UserRepositoryOutPort`: `save`, `findById`, … |
| `UserMapper` | Converte `UserModel` ↔ `UserEntity` |

O adapter **não** contém:

- validação de email;
- timestamps manuais;
- regras “se completed não atualiza”.

**Porquê?**  
Toda lógica de negócio fica no **serviço**. O repositório só **persiste e lê**.

### Entity vs Model (conceito crítico)

| | `UserEntity` | `UserModel` |
|---|--------------|-------------|
| Onde | `infrastructure/entity` | `domain/models` |
| Anotações JPA | Sim | Não |
| Usado por | Hibernate | Serviço, domínio |
| Pode mudar com BD | Sim | Não deve |

**Porquê dois objetos?**  
Para o negócio não ficar acoplado à tabela `users` e às colunas SQL.

---

## 10. Tema: Mappers (MapStruct)

### Porquê MapStruct

Evita código repetitivo de:

```java
dto.setId(model.getId());
dto.setName(model.getName());
// ... 20 linhas
```

### Métodos típicos neste projeto

| Método | Direção | Uso |
|--------|---------|-----|
| `toModel(Entity)` | BD → negócio | Após `find` |
| `toEntity(Model)` | negócio → BD | Antes de `save` |
| `toResponseDTO(Model)` | negócio → API | Resposta ao cliente |

### Regras importantes

1. **`toEntity` ignora `createdAt` / `updatedAt`** — a entidade preenche com auditoria JPA.
2. **`toResponseDTO` mapeia `user.id` → `userId`** no Todo (campo flat na API).
3. **`TodoMapper` usa `UserMapper`** — composição entre contextos na camada de mapeamento, não no serviço com SQL.

### Erro real que já aconteceu: `user_id` NULL

Se `toEntity` tiver `@Mapping(target = "user", ignore = true)`, o insert grava `user_id` NULL.

**Lição bootcamp:** mapper não é decorativo; é parte da correção de dados entre camadas.

---

## 11. Tema: Validação (declarativa vs negócio)

São **duas camadas diferentes**. Não confundir com “tipos de exceção”.

### Validação declarativa (Bean Validation)

- Onde: DTOs (`CreateUserRequestDTO`, …).
- Como: `@NotBlank`, `@Email`, `@NotNull`, `@Size`, `@AssertTrue`.
- Quando dispara: antes do serviço, no controller com `@Valid`.
- Quem trata: `MethodArgumentNotValidExceptionMapper` → HTTP 400, código `VALIDATION_ERROR`.

**Porquê?**  
“Campo vazio” é problema de **contrato de entrada**, não de regra de domínio rica.

### Validação de negócio (no serviço)

- Onde: `UserService`, `TodoService`.
- Como: `if (existsByEmail) throw new EmailAlreadyExistsException`.
- Quem trata: Exception mapper da exceção de domínio → HTTP 409, etc.

| Situação | Mecanismo |
|----------|-----------|
| Password com 3 caracteres | `@Size(min = 8)` no DTO |
| Email já registado | `EmailAlreadyExistsException` no serviço |
| Todo completed não atualiza | `TodoAlreadyCompletedException` no serviço |
| User ID inexistente ao criar todo | `UserNotFoundException` no serviço |

---

## 12. Tema: Exceções — o modelo completo

Este é o tema que mais confunde alunos. No projeto usamos **três peças** que trabalham juntas.  
⚠️ **Correção de vocabulário:** o termo *“exception interception”* (interceção de exceções) **não** é o nome correto para quando lançamos `throw` no código. Interceção/captura é papel do **Exception Mapper** (handler). O que fazemos no serviço chama-se **lançamento programático de exceções** (ou **disparo imperativo**).

---

### Peça 1 — Exceções de domínio declaradas (as classes)

**O que são:** classes Java que representam **falhas de negócio** conhecidas.

```
features/user/domain/exceptions/
├── UserDomainException.java      # base abstrata
├── UserNotFoundException.java
├── EmailAlreadyExistsException.java
└── InvalidUserDataException.java

features/todo/domain/exceptions/
├── TodoDomainException.java
├── TodoNotFoundException.java
├── InvalidTodoDataException.java
├── TodoAlreadyCompletedException.java
└── TodoCancelledException.java
```

Cada uma carrega um **`UserErrorCode` / `TodoErrorCode`**:

```java
USER_NOT_FOUND
EMAIL_ALREADY_EXISTS
INVALID_USER_DATA
TODO_NOT_FOUND
// ...
```

**Porquê códigos enum e não só mensagem?**

- Mensagem pode mudar (UX, idioma).
- Código é estável para logs, monitorização e clientes API.
- Mapper HTTP usa o código para status e corpo padronizado.

**Porquê hierarquia (`UserDomainException`)?**

- Agrupa erros do contexto User.
- Permite handler por tipo ou por família.
- Evita capturar `RuntimeException` genérico.

**Estas exceções são “declarativas”?**  
No sentido de **declaramos os tipos** no domínio (contrato de erros possíveis).  
**Não** confundir com validação declarativa (`@NotBlank`) — são coisas diferentes.

---

### Peça 2 — Exception Mappers (adaptadores REST)

**O que são:** classes na camada **adapter** que **capturam** uma exceção e **traduzem** para HTTP + JSON.

```
features/user/adapters/rest/exceptions/
├── UserNotFoundExceptionMapper.java
├── EmailAlreadyExistsExceptionMapper.java
└── InvalidUserDataExceptionMapper.java

features/todo/adapters/rest/exceptions/
├── TodoNotFoundExceptionMapper.java
└── ...

root/configs/exceptions/mappers/
└── MethodArgumentNotValidExceptionMapper.java   # erros de @Valid
```

Exemplo (Spring — equivalente ao padrão JAX-RS `ExceptionMapper`):

```java
@RestControllerAdvice
public class UserNotFoundExceptionMapper {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> toResponse(UserNotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponseDTO(
                exception.getCode().name(),
                exception.getMessage()
            ));
    }
}
```

**Porquê um mapper por exceção?**

- Responsabilidade única: 1 exceção → 1 status + 1 formato.
- Fácil de ensinar e de encontrar no código.
- Bootcamp vê claramente o mapa erro → HTTP.

**Porquê na camada adapter e não no domínio?**

- HTTP (`404`, `409`) é detalhe de **interface REST**.
- O domínio não deve saber o que é `ResponseEntity` ou `HttpStatus`.

| Exceção | HTTP típico |
|---------|-------------|
| `UserNotFoundException` | 404 |
| `EmailAlreadyExistsException` | 409 |
| `InvalidUserDataException` | 400 |
| `TodoAlreadyCompletedException` | 409 |
| Validação `@Valid` | 400 (`VALIDATION_ERROR`) |

**Resposta padrão:** `ErrorResponseDTO` em `root/shared`:

```json
{
  "code": "USER_NOT_FOUND",
  "message": "User not found: 3fa85f64-..."
}
```

---

### Peça 3 — Lançamento programático (no código de aplicação)

**Nome correto:** **lançamento programático de exceções** (em inglês: *programmatic exception throwing* / *imperative throw*).

**O que é:** quando o fluxo de negócio detecta uma falha e executa:

```java
throw new UserNotFoundException(id);
```

**Onde acontece neste projeto:** principalmente em `UserService` e `TodoService`.

Exemplos:

```java
// Recurso não encontrado
userRepository.findById(id)
    .orElseThrow(() -> new UserNotFoundException(id));

// Regra de negócio
if (userRepository.existsByEmail(email)) {
    throw new EmailAlreadyExistsException(email);
}

// Estado inválido do agregado
if (todo.getStatus() == TodoStatusEnum.COMPLETED) {
    throw new TodoAlreadyCompletedException(todo.getId());
}
```

**Porquê lançar em vez de retornar null ou boolean?**

- O fluxo feliz fica linear (menos `if (result == null)` espalhado).
- O erro sobe até um **mapper** que sabe traduzir para HTTP.
- O tipo da exceção documenta **o que correu mal**.

**Isto NÃO é “interceção”:**  
Quem **intercepta** (captura) é o `@RestControllerAdvice` no mapper.  
Quem **dispara** é o serviço com `throw`.

---

### Como as três peças dançam juntas (fluxo para o quadro)

```
1. Cliente HTTP POST /api/todos com body inválido
   → @Valid falha
   → MethodArgumentNotValidExceptionMapper
   → 400 VALIDATION_ERROR

2. Cliente POST /api/todos com userId inexistente
   → TodoService: throw UserNotFoundException
   → UserNotFoundExceptionMapper
   → 404 USER_NOT_FOUND

3. Cliente PUT todo COMPLETED
   → TodoService: throw TodoAlreadyCompletedException
   → TodoAlreadyCompletedExceptionMapper
   → 409 TODO_ALREADY_COMPLETED
```

**Analogia bootcamp:**

| Peça | Papel | Metáfora |
|------|-------|----------|
| Classe de exceção | Define o “tipo de problema” | Código de erro no manual |
| `throw` no serviço | Aplica regra e aciona alarme | Funcionário vê problema e assinala |
| Exception Mapper | Traduz alarme para linguagem do cliente | Receção explica ao visitante em português simples |

---

## 13. Tema: DTOs e modelos

| Tipo | Pasta | Quem vê |
|------|-------|---------|
| `CreateUserRequestDTO` | `application/dtos` | API entrada |
| `UserResponseDTO` | `application/dtos` | API saída |
| `UserModel` | `domain/models` | Serviço + portas |
| `UserEntity` | `infrastructure/entity` | JPA apenas |

**Porquê não expor `UserEntity` na API?**  
Porque a tabela tem `password`, relações lazy, colunas internas — vazaria detalhes e criaria acoplamento.

**Porquê classes e não records nos DTOs?**  
Decisão do projeto: DTOs mutáveis com getters/setters para Jackson e Bean Validation consistentes no bootcamp.

---

## 14. Tema: Persistência e auditoria de datas

### Onde vivem `createdAt` e `updatedAt`

Na **entidade JPA**, não no serviço:

```java
@EntityListeners(AuditingEntityListener.class)
public class TodoEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
```

`Application` com `@EnableJpaAuditing`.

**Porquê não `todo.setCreatedAt(Instant.now())` no serviço?**

- Duplica responsabilidade.
- Esquece-se fácil num `save`.
- A entidade é a fronteira com persistência — é lá que o ciclo de vida temporal faz sentido.

**Fluxo:** JPA preenche → mapper copia para `TodoModel` → `toResponseDTO` envia ao cliente.

---

## 15. Fluxo completo: criar um Todo

Passo a passo para explicar em aula:

```
POST /api/todos
Body: { "userId": "...", "title": "Estudar hexagonal" }
```

1. **Controller** recebe JSON, `@Valid` valida formato.
2. **TodoService.create**:
   - Busca user (`UserRepositoryOutPort`).
   - Se não existe → `throw UserNotFoundException` (lançamento programático).
   - Monta `TodoModel` (status PENDING, priority default).
3. **TodoRepositoryOutPort.save**:
   - `TodoMapper.toEntity` (com `user` mapeado!).
   - `todoJpaRepository.save`.
   - JPA preenche `created_at` / `updated_at`.
   - `TodoMapper.toModel` → `toResponseDTO`.
4. **Controller** devolve `201` + JSON.

Se `user` não for mapeado no `toEntity` → `user_id` NULL → erro SQL (lição prática).

---

## 16. Estratégia para ensinar no bootcamp (por aulas)

### Aula 1 — Mental model

- Hexágono, dependências para dentro.
- Feature folders.
- Sem código: desenhar User + Todo no quadro.

### Aula 2 — Domínio

- Criar `UserModel`, enums, exceções + `UserErrorCode`.
- **Sem Spring.**

### Aula 3 — Portas e serviço

- `UserRepositoryOutPort`, `UserServiceInPort`, `UserService`.
- Testes unitários com repositório em memória.

### Aula 4 — Infraestrutura

- `UserEntity`, auditoria, `UserJpaRepository`, adapter fino.
- MapStruct `toModel` / `toEntity` / `toResponseDTO`.

### Aula 5 — REST + validação

- DTOs com `@NotBlank`, controller `@Valid`.
- `MethodArgumentNotValidExceptionMapper`.

### Aula 6 — Exceções (as 3 peças)

1. Declarar classes no domínio.  
2. `throw` no serviço.  
3. Mappers HTTP.  
- Exercício: mapear tabela exceção → HTTP.

### Aula 7 — Feature Todo + relação User

- `ManyToOne`, `userId` no DTO, mapper com `user.id`.
- Erro `user_id` NULL como case study.

### Aula 8 — Refactor review

- Checklist nova feature (secção 17).
- Code review em pares.

---

## 17. Checklist: nova feature do zero

Exemplo: feature `tag` para etiquetas de todos.

- [ ] `domain/models/TagModel.java`
- [ ] `domain/exceptions/` + `TagErrorCode`
- [ ] `application/dtos/` (Create, Update, Response)
- [ ] `application/ports/in/TagServiceInPort`
- [ ] `application/ports/out/TagRepositoryOutPort`
- [ ] `application/services/TagService` (regras + `throw`)
- [ ] `infrastructure/entity/TagEntity` (auditoria se aplicável)
- [ ] `infrastructure/repository/` JPA + adapter fino
- [ ] `domain/mappers/TagMapper` (MapStruct)
- [ ] `adapters/rest/controllers/TagController` (`@Valid`)
- [ ] `adapters/rest/exceptions/*Mapper` (um por exceção)
- [ ] Testar fluxo feliz + 1 erro de negócio + 1 erro de validação

---

## 18. Erros comuns e como corrigir

| Sintoma | Causa provável | Correção |
|---------|----------------|----------|
| `user_id` NULL no insert | `user` ignorado no `toEntity` | Mapear `user` no `TodoMapper` |
| 500 em vez de 404 | Falta Exception Mapper | Criar mapper para essa exceção |
| Regra no repository | Lógica no adapter | Mover para serviço |
| Entity na API | Controller devolve `UserEntity` | Usar `UserResponseDTO` |
| Domínio importa Spring | `@Entity` no model | Separar Entity vs Model |
| Timestamps null | Sem `@EnableJpaAuditing` | Ativar na `Application` |
| Validação duplicada | `@NotBlank` + mesmo check no service | Formato no DTO; negócio no service |

---

## 19. Glossário rápido

| Termo | Significado |
|-------|-------------|
| **Port** | Interface/contrato entre camadas |
| **Adapter** | Implementação que liga mundo externo ao port |
| **Domain** | Regras e conceitos estáveis |
| **Application** | Casos de uso que orquestram o domínio |
| **Infrastructure** | BD, JPA, detalhes técnicos |
| **DTO** | Objeto de transferência na fronteira da API |
| **Exception Mapper** | Traduz exceção → HTTP + corpo JSON |
| **Lançamento programático** | `throw` de exceção de domínio no serviço |
| **Validação declarativa** | Anotações `@NotBlank`, `@Valid` nos DTOs |
| **Auditoria JPA** | `@CreatedDate` / `@LastModifiedDate` na entidade |

---

## Frase de fecho para o bootcamp

> **Hexagonal não é sobre pastas bonitas. É sobre o negócio no centro, os detalhes na borda, e contratos claros entre os dois — incluindo contratos de erro.**

Quando um aluno perguntar “onde põe X?”, responde com uma pergunta:

- *“Isto é regra de negócio, contrato de API, ou detalhe de base de dados?”*

A resposta diz a pasta.

---

*Documento alinhado ao projeto `todo.list` (Spring Boot, Java 17, H2, MapStruct).*
