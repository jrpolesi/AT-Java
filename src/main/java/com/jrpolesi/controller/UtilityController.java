package com.jrpolesi.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

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

        app.post("/echo", this::echo);
    }

    private void helloWorld(Context ctx) {
        ctx.contentType("text/plain; charset=UTF-8");
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
            ctx.status(400).json(
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
}
