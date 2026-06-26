# Git Hooks — validação de mensagens de commit

Valida que toda mensagem de commit siga `#NUM - MENSAGEM` e que a issue `#NUM`
exista no repositório no GitHub. Vale localmente (hook `commit-msg`) e no PR
(workflow `valida-commits-pr.yml`).

## Instalação (uma vez por clone)

No Git Bash, na raiz do projeto:

    git config core.hooksPath hooks
    git config core.commentChar ";"
    chmod +x hooks/commit-msg hooks/check-commit-msg.sh

- `core.hooksPath hooks` ativa os hooks versionados desta pasta.
- `core.commentChar ";"` evita que o Git apague a linha que começa com `#`.

## Teste rápido

    git commit --allow-empty -m "mensagem errada"        # rejeitado (formato)
    git commit --allow-empty -m "#999999 - inexistente"  # rejeitado (issue)
    git commit --allow-empty -m "#119 - teste"           # aceito

Para ignorar os hooks num commit específico: `git commit --no-verify`.