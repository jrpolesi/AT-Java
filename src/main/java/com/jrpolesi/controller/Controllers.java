package com.jrpolesi.controller;

import io.javalin.Javalin;

public class Controllers {
    private final Javalin app;

    public Controllers(Javalin app) {
        this.app = app;
    }

    public void init() {
        new UtilityController(app).initRoutes();
    }
}
