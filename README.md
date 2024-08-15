# BoletoGenerator

BoletoGenerator é uma aplicação Spring Boot que permite a criação, consulta, pagamento e exclusão de boletos. Este projeto demonstra o uso de Spring Boot, Spring Data JPA, Lombok e Swagger para construção de APIs RESTful.

## Funcionalidades

- *Criar Boleto:* Permite a criação de novos boletos.
- *Buscar Boletos por Pessoa:* Recupera boletos associados a um identificador de pessoa.
- *Buscar Boleto por ID:* Recupera um boleto específico pelo seu ID.
- *Pagar Boleto:* Atualiza o status do boleto com informações de pagamento.
- *Excluir Boleto:* Remove um boleto do sistema.

## Tecnologias Utilizadas

- *Java 17*
- *Spring Boot 3.3.2*
- *Spring Data JPA*
- *Spring Cloud OpenFeign*
- *H2 Database (para testes)*
- *Lombok*
- *Springdoc OpenAPI (Swagger)*
- *JUnit*

## Estrutura do Projeto

### Controller

O BoletoController expõe endpoints REST para gerenciar boletos:

- POST /boletos - Cria um novo boleto.
- GET /boletos/pessoas/{pessoaId}/boletos - Busca boletos por ID de pessoa.
- GET /boletos/{id} - Busca um boleto pelo seu ID.
- PUT /boletos/{id}/pagamento - Atualiza o pagamento de um boleto.
- DELETE /boletos/{id} - Exclui um boleto.

### Dependências

O projeto utiliza as seguintes dependências:

- spring-boot-starter
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-cloud-starter-openfeign
- h2 (para banco de dados em memória)
- springdoc-openapi-starter-webmvc-ui (para documentação Swagger)
- lombok (para simplificação do código)

## Configuração

1. *Clone o repositório:*
   ```bash
   git clone https://github.com/seu-usuario/BoletoGenerator.git
   cd BoletoGenerator