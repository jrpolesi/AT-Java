# AT Java

Este é um projeto Java para o Infnet, utilizando Javalin como framework web.

O Projeto foi separado em dois controllers, `UtilityController` e `ToDoController`.

O `UtilityController` contém os endpoints genéricos como, o de status, echo e o de executar o
exercício 3.

O `ToDoController` contém os endpoints relacionados ao caso de uso escolhido pelo professor.

## Pré-requisitos

- Java 21
- Gradle

## Clonar o repositório

1. Clone o repositório

```bash
git clone git@github.com:jrpolesi/AT-Java.git
```

2. Entre na pasta do projeto

```bash
cd AT-Java
```

## Endpoints disponíveis

O servidor roda em `http://localhost:7000`

### ToDo

#### Criar um novo ToDo

Curl de exemplo

```bash
curl --request POST \
  --url http://localhost:7000/todo \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.1.0' \
  --data '{
	"titulo": "Fazer AT C#",
	"descricao": "Fazer AT de dotnet do Infnet",
	"concluido": true
}'
```

#### Listar todos os ToDos

Curl de exemplo

```bash
curl --request GET \
  --url http://localhost:7000/todo \
  --header 'User-Agent: insomnia/11.1.0'
```

#### Buscar um ToDo por ID

Curl de exemplo

```bash
curl --request GET \
  --url http://localhost:7000/todo/:toDoId \
  --header 'User-Agent: insomnia/11.1.0'
```

### Exercício 3

Para o exercício 3, foi criado um endpoint que executa cada etapa do exercício e adiciona logs das
evidências de cada etapa

Curl para executar o exercício 3

```bash
curl --request GET \
  --url http://localhost:7000/exercicio3 \
  --header 'User-Agent: insomnia/11.1.0'
```

### Outros

#### Hello World

Curl de exemplo

```bash
curl --request GET \
  --url http://localhost:7000/hello \
  --header 'User-Agent: insomnia/11.0.2'
```

#### Status

Curl de exemplo

```bash
curl --request GET \
  --url http://localhost:7000/status \
  --header 'User-Agent: insomnia/11.1.0'
```

#### Echo

Curl de exemplo

```bash
curl --request POST \
  --url http://localhost:7000/echo \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.1.0' \
  --data '{
	"mensagem": "hello world"
}'
```

#### Saudação

Curl de exemplo

```bash
curl --request GET \
  --url http://localhost:7000/saudacao/:nome \
  --header 'User-Agent: insomnia/11.1.0'
```