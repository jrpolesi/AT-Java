package com.jrpolesi;

import com.jrpolesi.config.ProjectJavalinConfig;
import com.jrpolesi.controller.Controllers;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        final var app = Javalin.create(ProjectJavalinConfig::setConfig).start(ProjectJavalinConfig.PORT);

        final var controllers = new Controllers(app);
        controllers.init();
    }
}