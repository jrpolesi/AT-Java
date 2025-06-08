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
        app.get("/status", this::getStatus);
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
}
