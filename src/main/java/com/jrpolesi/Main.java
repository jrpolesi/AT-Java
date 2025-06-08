package com.jrpolesi;

import com.jrpolesi.controller.Controllers;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        final var app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json; charset=UTF-8";
        }).start(7000);

        final var controllers = new Controllers(app);
        controllers.init();
    }
}