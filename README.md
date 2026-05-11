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
mvn exec:java -Dexec.mainClass="com.sysbank.Main"
```

### Via Eclipse

1. `File → Import → Maven → Existing Maven Projects`
2. Selecione a pasta raiz do projeto
3. Botão direito no projeto → `Run As → Java Application`
4. Selecione `com.sysbank.Main`

### Executar Testes

```powershell
mvn test
```

---

## 📄 Licença

Projeto desenvolvido para fins **educacionais/acadêmicos**.

---

## ⭐ Sys Bank

> Simples, organizado e preparado para crescer.
