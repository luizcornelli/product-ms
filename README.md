# Desafio Catálogo de Produtos

## Informações sobre o projeto

Um microsserviço que disponibiliza operações a partir de um Catálogo de Produtos 

Cadastro de Produtos: 

* Nome

* Descrição

* Preço 

Opção de busca por parâmetros: 

* q = Busca por palavras que contenha na descrição ou nome no produto. 

* min_price = Busca pelo preço minimo. 

* max_price = Busca pelo preço máximo. 

Retorno:

Produtos cadastrados (Nome, Descrição e Preço)

### Tecnologias utilizadas

- Java 11
- Maven
- Junit / Mockito
- SpringBoot
- H2-Database

### Ferramentas

- Postman (Teste de endpoints)
- Swagger (Geração de documentação)
- Git (Controle de versão)
- GitHub (Versionamento Remoto)

### Os testes unitarios estão disponiveis em:

src/test/java/com.product > ProductMsApplicationTests

### API REST deve ser executada

src/main/java/com.product > ProductMsApplication > Run as SpringBoot

### Como executar

O projeto conta com uma classe ProductMsApplication dentro do módulo da API para startar a aplicação através do SpringInitializer

### Portas e endpoints

Porta: 9999

Endpoints Swagger-UI: http://localhost:9999/swagger-ui.html

Endpoint raiz Postman: http://localhost:9999/products/[operações]

Usuário H2-Database - Usuário: jdbc:h2:mem:testdb | Senha: (vazio)
