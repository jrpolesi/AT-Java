package com.jrpolesi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

class UtilityControllerTest {
    private static Javalin app;

    @BeforeAll
    static void startServer() {
        app = Javalin.create();
        new UtilityController(app).initRoutes();
    }

    @Test
    void WhenCallsHello_ShouldReturnHelloJavalin() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/hello");

            assertEquals(200, response.code());
            assertEquals("text/plain;charset=utf-8", response.header("Content-Type"));
            assertEquals("Hello, Javalin!", response.body().string());
        });
    }
} 