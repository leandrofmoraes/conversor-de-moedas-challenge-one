# Conversor de Moedas

Uma aplicação Java, via console para conversão monetária com integração em tempo real com uma API de câmbio.
O projeto foi desenvolvido como parte do desafio de programação, promovido pela escola de Tecnologia [Alura](https://www.alura.com.br) em parceria com a [Oracle Next Education (ONE)](https://www.oracle.com/br/education/oracle-next-education), com objetivo de demonstrar habilidades em Java.

## Features

- Conversão dinâmica de moedas via API em tempo real
- Listagem de moedas em colunas
- Dois modos de operação:
  - **Interativo**: Menu com navegação passo-a-passo
  - **Rápico**: Linha de comando, com passagem de argumentos.
- Suporte a múltiplos idiomas (i18n): PT-BR, EN, ES
- Geração de logs em arquivo
- Histórico de conversões em arquivos no formato JSON

## Requisitos

- Java 17+ (JDK ou JRE)
- Maven 3.9+ (para build e profiles)
- Chave de API válida do [ExchangeRate-API](https://www.exchangerate-api.com/)

## Configuração e obtenção da API Key

Este projeto consome dados de câmbio da API ExchangeRate, para usar é necessário obter sua própria chave:

- Acesse [ExchangeRate-API](https://www.exchangerate-api.com/)
- Registre-se gratuitamente
- Obtenha sua chave API na seção Dashboard

## Instalação

1. Exporte sua chave:
   ```bash
   export API_KEY=SUA_CHAVE_PROD
   ```

### Download Direto (Para Usuários Finais)

1. Baixe o JAR executável em [releases](https://github.com/leandrofmoraes/conversor-de-moedas-challenge-one/releases)

2. Execute:
   ```bash
   java -jar conversor-moedas-*.jar
   ```

### Para Desenvolvedores

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/conversor-moedas.git && cd conversor-moedas
```

2a. Exporte sua chave:

```bash
export API_KEY=SUA_CHAVE_PROD
```

2b. Ou edite o arquivo `src/main/resources/application-dev.properties`:

```properties
api.key=SUA_CHAVE_DE_TESTE
```

3a. Build e execute:

```bash
mvn clean package
java -jar target/conversor-moedas-*.jar
```

3b. Para ambiente de produção:

```bash
mvn clean package -Pprod
java -jar target/conversor-moedas-*.jar
```

## Uso

### Modo Interativo

Executa o menu passo-a-passo:

```bash
java -jar conversor-moedas.jar
```

![img](https://github.com/leandrofmoraes/conversor-de-moedas-challenge-one/blob/master/assets/Screenshot_10-mai.png)

### Modo Rápido (Linha de comando)

### Listar Moedas

```bash
java -jar conversor-moedas.jar --list
```

Passando `origem`, `destino` e `valor`:

```bash
# usando os códigos das moedas
java -jar conversor-moedas.jar USD BRL 100.50
```

ou

```bash
# usando os índices das moedas
java -jar conversor-moedas.jar 0 20 1.00
```

### Ajuda

```bash
java -jar conversor-moedas.jar --help
```

## Logs

Logs de nível INFO+ são salvos em logs/conversor.log

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "from": "USD",
  "to": "BRL",
  "amount": 100.0,
  "result": 490.5
}
```

## Histórico de Conversões

- Cada conversão é registrada no formato json no diretório do usuário em `~/.conversor-moedas/history.json`.
- O histórico mantém até 50 registros mais recentes.
- No modo interativo, selecione Exibir histórico para ver as últimas conversões.

## Idiomas Suportados

- Português (pt_BR)
- Inglês (en)
- Espanhol (es)

## Contribuições

Pull requests são bem-vindos! Para mudanças maiores, abra uma issue primeiro.

## Licença

MIT © Leandro F. Moraes
