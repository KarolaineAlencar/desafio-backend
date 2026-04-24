# Desafio Backend

Este microsserviço foi desenvolvido como parte de um desafio técnico para gerenciar e fornecer inteligência de dados 
sobre vendas de vinhos. A aplicação consome dados de mocks externos, realiza a persistência em um banco de dados 
relacional e expõe endpoints para análise de consumo, 
fidelidade e recomendações personalizadas.

## 🚀 Tecnologias Utilizadas

* **Java 21** (Linguagem principal)
* **Spring Boot 3.x** (Framework de desenvolvimento)
* **Spring Data JPA** (Persistência de dados)
* **PostgreSQL** (Banco de dados relacional)
* **Docker & Docker Compose** (Containerização do ambiente)
* **Lombok** (Produtividade e redução de boilerplate)
* **Jakarta Validation** (Garantia de integridade dos dados)

## 🛠️ Arquitetura e Diferenciais

A aplicação segue uma arquitetura em camadas (**Controller -> Service -> Repository**), garantindo separação 
de responsabilidades.
* **Persistência:** Os dados são carregados automaticamente via `DataSeeder` no startup da aplicação.
* **Versionamento:** Endpoints padronizados com o prefixo `/v1`.
* **DTOs:** Uso de Data Transfer Objects para isolar as entidades do banco de dados da camada de apresentação, 
incluindo cálculos dinâmicos de valores totais.

## 📋 Endpoints da API

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **GET** | `/v1/compras` | Lista compras ordenadas de forma crescente por valor. |
| **GET** | `/v1/maior-compra/{ano}` | Retorna a maior compra de um ano específico. |
| **GET** | `/v1/clientes-fieis` | Retorna o Top 3 clientes com maior volume financeiro em compras. |
| **GET** | `/v1/recomendacao/{clienteId}/tipo` | Recomenda um vinho baseado no tipo mais comprado pelo cliente. |

## 📦 Como Executar

### Pré-requisitos
* Docker e Docker Compose instalados.
* JDK 21.

### Passo 1: Subir o Banco de Dados
Na raiz do projeto, onde está o arquivo `docker-compose.yml`, execute:
```bash
docker-compose up -d
```

## 📖 Documentação da API (Swagger)

Uma vez que a aplicação esteja rodando, você pode acessar a documentação interativa e testar os endpoints através do Swagger UI:

🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)