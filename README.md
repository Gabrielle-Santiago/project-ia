# 🧠 Purchase Validation AI

Sistema de **validação automatizada de compras utilizando LLM local**, desenvolvido com **Java + Spring Boot** e integrado a um modelo executado via Ollama.

Este projeto demonstra como utilizar Inteligência Artificial como **motor de decisão controlado**, aplicando engenharia de prompt, validação estruturada e parsing seguro de respostas em JSON dentro de uma arquitetura backend tradicional.

---

## 🚀 Objetivo

Simular um sistema corporativo de validação de compras onde a decisão (**APPROVED** ou **REJECTED**) é tomada por um modelo de linguagem com base em regras de negócio definidas explicitamente no prompt.

A IA atua como uma camada de decisão, respeitando regras determinísticas e retornando uma resposta estruturada.

---

## 🏗️ Arquitetura

O projeto está organizado em camadas bem definidas:

- **Controller** → Recebe a requisição HTTP
- **Service (PurchaseValidationAIService)** → Constrói o prompt e processa a resposta
- **OllamaClient** → Responsável pela comunicação com o modelo
- **Records / DTOs** → Estruturas de request e response
- **Config** → WebClient + definição do modelo via `application.properties`

### 🔄 Fluxo da Aplicação

1. Recebe os dados da compra
2. Constrói um prompt estruturado com regras da empresa
3. Envia a requisição para o modelo via `/api/generate`
4. Recebe resposta em JSON
5. Faz parsing com `ObjectMapper`
6. Retorna decisão estruturada ao cliente

---

## 🧠 Engenharia de Prompt

O modelo recebe instruções claras:

- Papel definido: sistema automatizado de validação
- Regras de negócio explícitas
- Formato de saída obrigatório
- Proibição de texto fora do JSON

### 📌 Formato esperado da resposta

```json
{
  "status": "APPROVED or REJECTED",
  "reason": "short explanation in Brazilian Portuguese"
}
```

Esse controle reduz alucinações e permite parsing seguro no backend.

## 📋 Regras de Negócio

A validação considera:

- Comprador deve ter pelo menos 18 anos
- Quantidade mínima: 1
- Quantidade máxima: 1000
- Apenas cartões de crédito nacionais
- Apenas categoria "electronics"

## 🔌 Integração com LLM

O modelo é executado localmente via Ollama (ex: phi3:mini).

Configuração:

```ai.ollama.model=phi3:mini```

### 🎯 O que este projeto demonstra

1. Como integrar LLM em aplicações Spring Boot
2. Como estruturar prompts para decisões determinísticas
3. Como reduzir risco de respostas inválidas
4. Como transformar IA em motor de regra de negócio
5. Como organizar arquitetura escalável com IA

## 📦 Tecnologias Utilizadas

- Java
- Spring Boot
- WebClient
- Jackson (ObjectMapper)
- Ollama (LLM local)

### 🧪 Exemplo de Uso
Request

```json{
  "age": 25,
  "quantity": 2,
  "payment": "NATIONAL_CREDIT_CARD",
  "category": "electronics"
}
```

Response
```json{
  "status": "APPROVED",
  "reason": "Compra atende todas as regras da empresa."
}
```

