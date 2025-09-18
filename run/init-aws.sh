#!/bin/bash

# Sai do script se qualquer comando falhar
set -e

echo "########### Iniciando configuração dos recursos AWS no LocalStack ###########"

# Habilita o log de comandos para facilitar a depuração
set -x

# --- Configurações ---
REGION="us-east-1"
QUEUE_NAME="zips-gerados-queue"
TOPIC_NAME="notificacoes-topico"

# --- Criação da Fila SQS ---
echo "=> Criando Fila SQS: ${QUEUE_NAME}"
awslocal sqs create-queue --queue-name "${QUEUE_NAME}" --region "${REGION}"

# --- Criação do Tópico SNS ---
echo "=> Criando Tópico SNS: ${TOPIC_NAME}"
awslocal sns create-topic --name "${TOPIC_NAME}" --region "${REGION}"

# Desabilita o log de comandos
set +x

echo "########### ✅ Configuração do LocalStack concluída com sucesso! ###########"