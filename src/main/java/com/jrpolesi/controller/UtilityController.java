package com.jrpolesi.controller;

import com.jrpolesi.config.ProjectJavalinConfig;
import com.jrpolesi.dto.ToDoRequestDTO;
import com.jrpolesi.dto.ToDoResponseDTO;
import com.jrpolesi.utils.HttpClient.HttpClient;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class UtilityController implements IController {
    private final Javalin app;

    public UtilityController(Javalin app) {
        this.app = app;
    }

    @Override
    public void initRoutes() {
        app.get("/hello", this::helloWorld);
        app.get("/saudacao/{nome}", this::greeting);
        app.get("/status", this::getStatus);
        app.get("/exercicio3", this::exercicio3);

        app.post("/echo", this::echo);
    }

    private void helloWorld(Context ctx) {
        ctx.contentType("text/plain;charset=utf-8");
        ctx.result("Hello, Javalin!");
    }

    private void getStatus(Context ctx) {
        final var timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        ctx.json(Map.of(
                "status", "ok",
                "timestamp", timestamp
        ));
    }

    private void echo(Context ctx) {
        final var body = ctx.bodyAsClass(Map.class);
        final var message = body.get("mensagem");

        if (message == null) {
            ctx.status(HttpStatus.BAD_REQUEST).json(
                    Map.of("erro", "A propriedade 'mensagem' é obrigatória.")
            );
            return;
        }

        ctx.json(body);
    }

    private void greeting(Context ctx) {
        final var nome = ctx.pathParam("nome");
        final var message = String.format("Olá, %s!", nome);

        ctx.json(Map.of("mensagem", message));
    }

    private void exercicio3(Context ctx) {
        System.out.println("Executando o exercício 3...\n");
        final var baseURL = String.format("http://localhost:%d", ProjectJavalinConfig.PORT);


        try {
            // Parte 1 do exercício 3
            final var createdToDoId = stepOneOfExercise3(baseURL);
            System.out.println("\n");

            // Parte 2 do exercício 3
            stepTwoOfExercise3(baseURL);
            System.out.println("\n");

            // Parte 3 do exercício 3
            stepThreeOfExercise3(baseURL, createdToDoId);
            System.out.println("\n");

            // Parte 4 do exercício 3
            stepFourOfExercise3(baseURL);
            System.out.println("\n");

            ctx.json(Map.of(
                    "mensagem", "Exercício 3 executado com sucesso, verifique os logs no console"
            ));
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(
                    Map.of("erro", "Erro ao executar o exercício 3: " + e.getMessage())
            );
        }
    }

    private String stepOneOfExercise3(String baseURL) throws Exception {
        final var todoRequest = new ToDoRequestDTO("Teste", "Teste para o exercício 3", true);

        final var endpoint = "/todo";
        final var response = HttpClient.post(baseURL + endpoint, todoRequest, ToDoResponseDTO.class);
        final var body = response.getBody();

        System.out.println("Parte 1 - Item criado: ");
        printToDoResponse(body);

        return body.id();
    }

    private void stepTwoOfExercise3(String baseURL) throws Exception {
        final var endpoint = "/todo";
        final var response = HttpClient.get(baseURL + endpoint, ToDoResponseDTO[].class);

        System.out.println("Parte 2 - Listagem de itens: ");
        printToDoResponse(response.getBody());
    }

    private void stepThreeOfExercise3(String baseURL, String toDoId) throws Exception {
        final var endpoint = "/todo/";
        final var url = baseURL + endpoint + toDoId;
        final var response = HttpClient.get(url, ToDoResponseDTO.class);

        System.out.println("Parte 3 - Buscar item por ID: ");
        printToDoResponse(response.getBody());
    }

    private void stepFourOfExercise3(String baseURL) throws Exception {
        final var endpoint = "/status";
        final var response = HttpClient.get(baseURL + endpoint, Map.class);

        System.out.println("Parte 4 - Exibir status: ");
        System.out.println("Status da API: " + response.getBody());
    }

    private void printToDoResponse(ToDoResponseDTO[] responses) {
        for (ToDoResponseDTO response : responses) {
            printToDoResponse(response);
        }
    }

    private void printToDoResponse(ToDoResponseDTO response) {
        System.out.printf(
                "ID: %s, Título: %s, Descrição: %s, Concluído: %s, Data de Criação: %s\n",
                response.id(),
                response.titulo(),
                response.descricao(),
                response.concluido(),
                response.dataCriacao()
        );
    }
}
