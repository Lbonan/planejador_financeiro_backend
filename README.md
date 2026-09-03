# 💰 Planejador Financeiro — Backend
 
Backend de um sistema de planejamento financeiro pessoal, desenvolvido em **Java** com **Spring Boot**. A API permite que um usuário gerencie suas contas bancárias, categorize receitas e despesas, registre transações e transferências, e acompanhe metas financeiras.
 
> ⚠️ **Status:** projeto em desenvolvimento ativo, feito como estudo em grupo. Este README reflete o estado atual da aplicação e será atualizado conforme novas funcionalidades forem concluídas.
 
---
 
## 👥 Equipe
 
Projeto desenvolvido em grupo por:
 
- **Lucas Bonan** 
- **Carlos Eduardo**
- **João Gabriel Barros**
---
 
## 📌 Sobre o projeto
 
O **Planejador Financeiro** nasceu como um projeto de estudo em grupo, com o objetivo de aplicar na prática conceitos de desenvolvimento backend com Spring Boot: modelagem de domínio, arquitetura em camadas, persistência com JPA/Hibernate, validação de dados, tratamento centralizado de exceções e documentação de API.
 
A proposta do sistema é permitir que um usuário:
 
- Cadastre suas **contas bancárias** (corrente, poupança) e acompanhe o saldo de cada uma;
- Organize receitas e despesas por **categorias** personalizadas;
- Registre **transações** (receitas e despesas) vinculadas a uma conta e categoria;
- Realize **transferências** entre contas próprias;
- Defina **metas financeiras** com valor objetivo e prazo, acompanhando o progresso.
Cada usuário só enxerga e manipula os próprios dados — contas, categorias, transações, transferências e metas são sempre vinculadas a um `usuario_id`.
 
---
 
## 🛠️ Tecnologias utilizadas
 
| Tecnologia | Finalidade |
|---|---|
| **Java 17** | Linguagem principal do projeto |
| **Spring Boot 4.1.1** | Framework base da aplicação |
| **Spring Data JPA** (Hibernate) | Persistência e mapeamento objeto-relacional |
| **Spring Security** | Base para autenticação e autorização |
| **Spring Web (MVC)** | Construção da API REST |
| **Bean Validation** (`spring-boot-starter-validation`) | Validação dos dados recebidos pela API |
| **PostgreSQL** | Banco de dados relacional |
| **springdoc-openapi (Swagger)** | Documentação interativa da API |
| **Lombok** | Redução de código repetitivo (getters, setters, construtores) |
| **Maven** | Gerenciador de dependências e build |
| **Spring Boot DevTools** | Produtividade em desenvolvimento (reload automático) |
 
---
 
## 🏗️ Arquitetura
 
O projeto segue uma **arquitetura em camadas**, separando claramente as responsabilidades:
 
```
Controller  →  Service  →  Repository  →  Banco de Dados
   (API)      (regras de     (acesso a
              negócio)         dados)
```
 
- **`api.controller`** — expõe os endpoints REST, recebe requisições HTTP e devolve respostas. Não contém regra de negócio, apenas orquestra a chamada ao `Service`.
- **`api.dto`** — objetos de transferência de dados (Request/Response) usados na entrada e saída da API. A API nunca expõe as entidades JPA diretamente, evitando acoplamento entre o contrato da API e o modelo do banco.
- **`service`** — contém as regras de negócio: validações de domínio, orquestração entre repositórios, conversão entre DTO e entidade.
- **`model.entity`** — as entidades JPA, que representam as tabelas do banco de dados.
- **`model.repository`** — interfaces `JpaRepository` responsáveis pelo acesso direto aos dados.
- **`exceptions`** — exceções customizadas (ex: `RecursoNaoEncontradoException`) e um `GlobalExceptionHandler` (`@RestControllerAdvice`) que centraliza o tratamento de erros de toda a API, garantindo respostas padronizadas (status HTTP correto + corpo de erro consistente) em qualquer endpoint do projeto.
---
 
## 🗂️ Estrutura do projeto
 
```
financeiro/
├── src/
│   └── main/
│       └── java/
│           └── planejador/finaceiro/
│               ├── api/
│               │   ├── controller/       # Endpoints REST
│               │   │   ├── ContaController.java
│               │   │   ├── CategoriaController.java
│               │   │   ├── TransacaoController.java
│               │   │   ├── TransferenciaController.java
│               │   │   └── UsuarioController.java
│               │   └── dto/              # Objetos de entrada/saída da API
│               │
│               ├── model/
│               │   ├── entity/           # Entidades JPA (tabelas do banco)
│               │   │   ├── Usuario.java
│               │   │   ├── Conta.java
│               │   │   ├── Categoria.java
│               │   │   ├── Transacao.java
│               │   │   ├── Transferencia.java
│               │   │   ├── Meta.java
│               │   │   └── (enums: TipoConta, TipoCategoria, TipoTransacao, StatusMeta...)
│               │   │
│               │   └── repository/       # Interfaces de acesso a dados
│               │
│               ├── service/              # Regras de negócio
│               │
│               └── exceptions/           # Tratamento centralizado de erros
│                   ├── GlobalExceptionHandler.java
│                   └── RecursoNaoEncontradoException.java
│
├── pom.xml
└── mvnw / mvnw.cmd
```
 
---
 
## 🧩 Modelo de domínio
 
| Entidade | Descrição |
|---|---|
| **Usuario** | Dono de todos os dados financeiros: contas, categorias, transações, transferências e metas. |
| **Conta** | Conta bancária do usuário (corrente ou poupança), com saldo inicial e vínculo a um `Usuario`. |
| **Categoria** | Classificação de receita ou despesa (ex: Alimentação, Salário), vinculada a um `Usuario`. |
| **Transacao** | Registro de uma receita ou despesa, vinculada a uma `Conta` e a uma `Categoria`. |
| **Transferencia** | Movimentação de valores entre duas contas do mesmo usuário (conta de origem e conta de destino). |
| **Meta** | Objetivo financeiro do usuário, com valor alvo, valor atual, prazo (`dataLimite`) e status de progresso. |
 
O modelo completo, com todos os atributos e cardinalidades das relações, está representado no **Diagrama Entidade-Relacionamento (DER)** do projeto — veja a seção [Modelagem do banco de dados](#-modelagem-do-banco-de-dados) abaixo.
 
---
 
## 📡 Endpoints da API
 
A API segue o padrão REST, com recursos versionados por caminho (`/api/{recurso}`). Todas as respostas de erro seguem o formato padronizado do `GlobalExceptionHandler`.
 
| Recurso | Endpoints implementados |
|---|---|
| **Contas** | `POST /api/contas` · `GET /api/contas` · `GET /api/contas/{id}` · `PUT /api/contas/{id}` · `DELETE /api/contas/{id}` |
| **Categorias** | `POST /api/categorias` · `GET /api/categorias` · `GET /api/categorias/{id}` · `PUT /api/categorias/{id}` · `DELETE /api/categorias/{id}` |
| **Transações** | `POST /api/transacoes` · `GET /api/transacoes` · `GET /api/transacoes/{id}` · `DELETE /api/transacoes/{id}` |
| **Transferências** | endpoints de transferência entre contas (ver documentação Swagger) |
| **Usuários** | endpoints de cadastro e consulta de usuário (ver documentação Swagger) |
| **Metas** | modelo e repositório prontos — endpoints em desenvolvimento |
 
A documentação completa e interativa de cada endpoint (parâmetros, corpo de requisição, respostas possíveis) fica disponível via **Swagger UI** assim que a aplicação está rodando:
 
```
http://localhost:8080/swagger-ui.html
```
 
---
 
## ⚙️ Como executar o projeto
 
### Pré-requisitos
 
- Java 17+
- PostgreSQL instalado e rodando
- Maven (ou usar o Maven Wrapper incluso: `mvnw` / `mvnw.cmd`)
### Passo a passo
 
1. **Clone o repositório**
```bash
   git clone https://github.com/Lbonan/planejador_financeiro_backend.git
   cd planejador_financeiro_backend/financeiro
```
 
2. **Configure o banco de dados**
   Crie um banco PostgreSQL para o projeto e configure as credenciais em `src/main/resources/application.properties` (ou via variáveis de ambiente):
```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/planejador_financeiro
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
   spring.jpa.hibernate.ddl-auto=update
```
 
3. **Execute a aplicação**
```bash
   ./mvnw spring-boot:run
```
   (Windows: `mvnw.cmd spring-boot:run`)
 
4. **Acesse a documentação da API**
```
   http://localhost:8080/swagger-ui.html
```
 
---
 
## 🌿 Organização do trabalho em equipe
 
O desenvolvimento é dividido por funcionalidade, com uma branch de feature por membro/módulo (ex: `feature/Conta`, `feature/Categoria`, `feature/transacoes`, `feature/transferencia`, `feature/usuario`, `feature/meta`), integradas à branch `main` via pull request.
 
O acompanhamento de tarefas e o board do time estão organizados no **Trello** — print da organização atual:
 
> 📋 *[Espaço reservado para a imagem do board do Trello]*
 
---
 
## 🗺️ Modelagem do banco de dados
 
O modelo de dados completo (entidades, atributos, chaves primárias/estrangeiras e cardinalidade dos relacionamentos) foi desenhado em um **Diagrama Entidade-Relacionamento (DER)**, mantido em um repositório separado dedicado à modelagem do projeto:
 
> 🔗 *[Espaço reservado para o link do repositório de modelagem]*
 
Imagem do modelo atual:
 
> 🗺️ *[Espaço reservado para a imagem do DER]*
 
---
 
## 🚧 Próximos passos
 
- [ ] Finalizar os endpoints de **Metas**
- [ ] Implementar autenticação/autorização com Spring Security (JWT)
- [ ] Adicionar testes automatizados (unitários e de integração)
- [ ] Popular categorias padrão via seed inicial
- [ ] Publicar o link do repositório de modelagem (DER) e o board do Trello
---
 
## 📄 Licença
 
Projeto desenvolvido para fins de estudo. Licença a ser definida.
