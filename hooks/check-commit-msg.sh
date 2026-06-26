#!/bin/sh
# Validador de mensagem de commit (Tarefa 4 - DIM0517)
# Uso: check-commit-msg.sh "<assunto do commit>"
#   Verif. 1: formato  "#NUM - MENSAGEM"  (aceita '-' ou en-dash '–')
#   Verif. 2: a issue #NUM existe no repositorio do GitHub
set -u

SUBJECT="${1:-}"
REPO="${REPO:-Elildes/sysbank}"

# remove espacos das pontas
SUBJECT=$(printf '%s' "$SUBJECT" | sed 's/^[[:space:]]*//; s/[[:space:]]*$//')

# ----- Verificacao 1: formato -----
if ! printf '%s' "$SUBJECT" | grep -qP '^#[0-9]+\s*(-|\xe2\x80\x93)\s*\S.*$'; then
  echo "[hook] ERRO de FORMATO: use '#NUM - MENSAGEM' (ex.: #33 - Corrige a tela principal)."
  echo "[hook]   Mensagem recebida: '$SUBJECT'"
  exit 1
fi

NUM=$(printf '%s' "$SUBJECT" | grep -oP '^#\K[0-9]+')

# ----- Verificacao 2: a issue existe no GitHub -----
API="https://api.github.com/repos/$REPO/issues/$NUM"
if [ -n "${GITHUB_TOKEN:-}" ]; then
  CODE=$(curl -s -o /dev/null -w '%{http_code}' -H "Authorization: Bearer $GITHUB_TOKEN" "$API")
else
  CODE=$(curl -s -o /dev/null -w '%{http_code}' "$API")
fi

case "$CODE" in
  200) echo "[hook] OK: #$NUM e uma issue valida em $REPO."; exit 0 ;;
  404) echo "[hook] ERRO: a issue #$NUM NAO existe em $REPO."; exit 1 ;;
  403) echo "[hook] ERRO: limite da API do GitHub (HTTP 403). Defina GITHUB_TOKEN (60->5000/h)."; exit 1 ;;
  *)   echo "[hook] ERRO: nao foi possivel verificar a issue #$NUM (HTTP $CODE)."; exit 1 ;;
esac