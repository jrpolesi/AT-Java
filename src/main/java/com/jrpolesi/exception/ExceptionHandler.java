package com.jrpolesi.exception;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Map;

public class ExceptionHandler {
    public static void handleGenericException(Exception e, Context ctx) {
        ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        ctx.json(Map.of(
                "erro", "Ocorreu um erro inesperado."
        ));
    }
} 