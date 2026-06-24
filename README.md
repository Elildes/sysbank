# 💳 Sys Bank — Sistema Bancário

> Projeto acadêmico desenvolvido para a disciplina **DIM0517 - Gerência de Configuração e Mudanças**.

---

## 📌 Sobre o Projeto

O **Sys Bank** é um sistema bancário em ambiente **console/terminal**, desenvolvido em **Java**, com foco em boas práticas de versionamento, organização em camadas e uso de fluxo colaborativo com GitHub.

O projeto contempla operações bancárias essenciais e arquitetura preparada para evolução futura, incluindo testes unitários e acesso aos dados localmente.

---

## 👥 Equipe de Desenvolvimento

| Integrante                    | Função        | GitHub       |
| ----------------------------- | ------------- | ------------ |
| Elildes Fortaleza Santos      | Desenvolvedor | @Elildes     |
| Manuel Jonas Fonseca Barbalho | Desenvolvedor | @manueljonas |

---

## 🛠️ Stack de Desenvolvimento

### Linguagem Principal

* **Java** (JDK 17+ recomendado)

### IDEs Utilizadas

* **Visual Studio Code**
* **Eclipse IDE**

### Execução

* Aplicação via **Terminal / Console**

### Persistência de Dados

* **Sem banco de dados implementado nesta etapa**
* Utilização de **dados iniciais com setup inicial em arquivo** para testes e demonstração

### Ferramentas de Versionamento

* **Git**
* **GitHub**
* **GitLab Flow** como estratégia de branches

### Bibliotecas / Frameworks Recomendados

* `Java Collections Framework`
* `JUnit 5` (testes unitários futuros)
* `Maven` (gerenciamento de dependências)
* `Checkstyle` / `SpotBugs` *(qualidade de código)*

---

## 🧩 Funcionalidades Previstas

O sistema contempla **5 operações principais**:

1. ✅ Cadastrar Conta
2. ✅ Consultar Saldo
3. ✅ Crédito em Conta
4. ✅ Débito em Conta
5. ✅ Transferência entre Contas

---

## 🏗️ Arquitetura do Projeto

Separação em camadas com o padrão MCV:

```text
src/
├── main/
│   └── java/
│       ├── view/         -> interação com usuário (terminal)
│       ├── controller/   -> controle do fluxo da aplicação
│       ├── service/      -> regras de negócio
│       └── model/        -> entidades do sistema
└── test/
    └── java/            -> testes unitários

```

---

## 🌿 Estratégia de Branches

Utilização do padrão **GitLab Flow** conforme material da disciplina:

### Branches Principais

* `main` → branch principal de **desenvolvimento** com versão atualizada do sistema em construção
* `staging` → ambiente de **homologação / pré-produção**, usado para estabilização da próxima versão
* `production` → ambiente de **produção**, contendo a versão liberada aos usuários

### Branches de Suporte

* `feature/*` → novas funcionalidades criadas a partir da `main`
* `bugfix/*` → correções criadas a partir da `staging`, com merge em `staging` e `main`
* `hotfix/*` → correções urgentes criadas a partir da `production`, com merge em `staging` e `production`

> Conforme especificação da disciplina, as branches não serão removidas durante o desenvolvimento do projeto.

---

## 🚀 Como Executar

```bash
# Clonar o repositório
git https://github.com/Elildes/sysbank.git

# Entrar na pasta do projeto
cd sys-bank

# Compilar
javac Main.java

# Executar
java Main
```

---

## 📚 Objetivo Acadêmico

Este projeto prioriza:

* Boas práticas de versionamento
* Rastreabilidade entre tarefas e commits
* Trabalho colaborativo em equipe
* Organização arquitetural
* Evolução incremental do software

---

## 🚀 Como Executar

### Pré-requisitos
- JDK 17 ou superior
- Maven 3.6 ou superior

### Via Maven (recomendado)

```powershell
# Na raiz do projeto
mvn compile
mvn exec:java -Dexec.mainClass="com.sysbank.Main_Old"
```

### Via Eclipse

1. `File → Import → Maven → Existing Maven Projects`
2. Selecione a pasta raiz do projeto
3. Botão direito no projeto → `Run As → Java Application`
4. Selecione `com.sysbank.Main_Old`

### Executar Testes

```powershell
mvn test
```

---

## 🐳 Executar via Docker

Imagem publicada no Docker Hub (apenas a API REST, na porta 8080):

🔗 **Docker Hub:** https://hub.docker.com/r/manueljonas/sysbank

```bash
docker pull manueljonas/sysbank:latest
docker run -p 8080:8080 manueljonas/sysbank:latest
```

A API fica disponível em `http://localhost:8080/banco/conta`.

## 🔌 Endpoints da API REST

| Operação | Método | Endpoint | Corpo (JSON) |
|---|---|---|---|
| Cadastrar conta | POST | `/banco/conta/` | `{"numero":1,"tipo":"simples","saldoInicial":500}` |
| Consultar conta | GET | `/banco/conta/{id}` | — |
| Consultar saldo | GET | `/banco/conta/{id}/saldo` | — |
| Crédito | PUT | `/banco/conta/{id}/credito` | `{"valor":100}` |
| Débito | PUT | `/banco/conta/{id}/debito` | `{"valor":50}` |
| Transferência | PUT | `/banco/conta/transferencia` | `{"from":1,"to":2,"amount":50}` |
| Render juros (todas poupanças) | PUT | `/banco/conta/rendimento` | `{"taxa":1}` |

Exemplo:
```bash
curl -X POST http://localhost:8080/banco/conta/ -H "Content-Type: application/json" -d '{"numero":1,"tipo":"simples","saldoInicial":500}'
curl http://localhost:8080/banco/conta/1
```

## 📄 Licença

Projeto desenvolvido para fins **educacionais/acadêmicos**.

---

## ⭐ Sys Bank

> Simples, organizado e preparado para crescer.
