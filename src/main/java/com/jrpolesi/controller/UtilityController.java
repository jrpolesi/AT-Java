package com.jrpolesi.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class UtilityController implements IController {
    private final Javalin app;

    public UtilityController(Javalin app) {
        this.app = app;
    }

    @Override
    public void initRoutes() {
        app.get("/hello", this::helloWorld);
    }

    private void helloWorld(Context ctx) {
        ctx.contentType("text/plain; charset=UTF-8");
        ctx.result("Hello, Javalin!");
    }
}
