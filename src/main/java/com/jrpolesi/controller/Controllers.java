package com.jrpolesi.controller;

import com.jrpolesi.repository.InMemoryToDoRepository;
import com.jrpolesi.service.ToDoService;
import io.javalin.Javalin;

public class Controllers {
    private final Javalin app;

    public Controllers(Javalin app) {
        this.app = app;
    }

    public void init() {
        final var toDoRepository = new InMemoryToDoRepository();
        final var toDoService = new ToDoService(toDoRepository);

        new ToDoController(app, toDoService).initRoutes();
        new UtilityController(app).initRoutes();
    }
}
