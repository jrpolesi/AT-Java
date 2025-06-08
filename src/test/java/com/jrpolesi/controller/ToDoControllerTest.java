package com.jrpolesi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrpolesi.dto.ToDoResponseDTO;
import com.jrpolesi.repository.InMemoryToDoRepository;
import com.jrpolesi.service.ToDoService;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ToDoControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Javalin app;

    @BeforeEach
    void setup() {
        app = Javalin.create();
        var repository = new InMemoryToDoRepository();
        var service = new ToDoService(repository);
        final var toDoController = new ToDoController(app, service);
        toDoController.initRoutes();
    }

    private void assertToDoResponse(String response, String titulo, String descricao, boolean concluido) throws Exception {
        var responseJson = objectMapper.readValue(response, ToDoResponseDTO.class);

        assertNotNull(responseJson.id());
        assertNotNull(responseJson.dataCriacao());
        assertEquals(titulo, responseJson.titulo());
        assertEquals(descricao, responseJson.descricao());
        assertEquals(concluido, responseJson.concluido());
    }

    @Test
    void givenValidBody_whenCallsPOSTToDo_shouldReturn201() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var titulo = "Fazer AT Java";
            var descricao = "Implementar testes para API REST";
            var concluido = false;

            var todoRequest = """
                    {
                        "titulo": "%s",
                        "descricao": "%s",
                        "concluido": %b
                    }
                    """.formatted(titulo, descricao, concluido);

            var response = client.post("/todo", todoRequest);

            assertEquals(201, response.code());
            assertEquals("application/json", response.header("Content-Type"));
            assertToDoResponse(response.body().string(), titulo, descricao, concluido);
        });
    }
} 