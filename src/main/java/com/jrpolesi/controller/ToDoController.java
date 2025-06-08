package com.jrpolesi.controller;

import com.jrpolesi.dto.ToDoRequestDTO;
import com.jrpolesi.service.ToDoService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.Map;

public class ToDoController implements IController {
    private final Javalin app;
    private final ToDoService toDoService;

    public ToDoController(Javalin app, ToDoService toDoService) {
        this.app = app;
        this.toDoService = toDoService;
    }

    @Override
    public void initRoutes() {
        app.post("/todo", this::createToDo);
        app.get("/todo", this::findAllToDos);
    }

    private void createToDo(Context ctx) {
        ToDoRequestDTO toDoIntent = null;

        try {
            toDoIntent = ctx.bodyAsClass(ToDoRequestDTO.class);
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(Map.of("erro", "O body está faltando ou é inválido"));
            return;
        }

        final var errors = validateToDoRequest(toDoIntent);
        if (errors != null) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(Map.of(
                    "erro", errors
            ));
            return;
        }

        final var createdToDo = toDoService.createToDo(toDoIntent);

        ctx.status(HttpStatus.CREATED);
        ctx.json(createdToDo);
    }

    public void findAllToDos(Context ctx) {
        final var toDoList = toDoService.findAllToDos();
        ctx.json(toDoList);
    }

    private String validateToDoRequest(ToDoRequestDTO toDoRequest) {
        final var errors = new ArrayList<String>();
        if (toDoRequest.titulo() == null || toDoRequest.titulo().isBlank()) {
            errors.add("A propriedade 'titulo' é obrigatória");
        }
        if (toDoRequest.descricao() == null || toDoRequest.descricao().isBlank()) {
            errors.add("A propriedade 'descricao' é obrigatória");
        }
        if (!errors.isEmpty()) {
            return String.join("; ", errors);
        }

        return null;
    }
}
