# Conversor de Moedas

Um projeto Java de console para converter valores entre diferentes moedas usando taxas obtidas de uma API externa.

## Features

- Conversão dinâmica de moedas via API em tempo real
- Dois modos de interação (Via menus e usando parâmetros, via linha de comando)
- Suporte a múltiplos idiomas (i18n): PT-BR, EN, ES
- Listagem de moedas disponíveis em colunas alinhadas

## Requisitos

- Java 17+ (JDK ou JRE)
- Maven 3.9+ (para build e profiles)

## Configuração da API Key

1. Exporte sua chave:

   ```bash
   export API_KEY=SUA_CHAVE_PROD
   ```

2. Build e execute:

   ```bash
   mvn clean package -Pprod
   java -jar target/conversor-moedas-*-shaded.jar
   ```

### Para Desenvolvimento (arquivo de properties)

1. Edite `src/main/resources/application-dev.properties`:

   ```properties
   api.key=SUA_CHAVE_DE_TESTE
   ```

2. Build e execute:

   ```bash
   mvn clean package
   java -jar target/conversor-moedas-*-shaded.jar
   ```

## Instalação

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/conversor-moedas.git
   cd conversor-moedas
   ```

2. Build:

   ```bash
   mvn clean package
   ```

3. (Opcional) Gere o JAR executável com dependências:

   ```bash
   mvn package
   ```

## Uso

### Modo Interativo

Executa o menu passo-a-passo:

```bash
java -jar conversor-moedas.jar
```

### Modo Linha de comando

### Listar Moedas

```bash
java -jar conversor-moedas.jar --list
```

Passando `origem`, `destino` e `valor`:

```bash
# usando os códigos das moedas
java -jar conversor-moedas.jar USD BRL 100.50
```

```bash
# usando os índices das moedas
java -jar conversor-moedas.jar 0 20 1.00
```

### Ajuda

```bash
java -jar conversor-moedas.jar --help
```

## Idiomas Suportados

- Português (pt_BR)
- Inglês (en)
- Espanhol (es)

## Contribuições

Pull requests são bem-vindos! Por favor, abra uma issue primeiro para discutir mudanças maiores.

## Licença

MIT © Leandro F. Moraes
