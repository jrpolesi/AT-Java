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
    private static final String CONTENT_TYPE = "application/json";
    private static final String DEFAULT_TITULO = "Fazer AT Java";
    private static final String DEFAULT_DESCRICAO = "Implementar testes para API REST";
    private static final boolean DEFAULT_CONCLUIDO = false;

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

    @Test
    void givenValidBody_whenCallsPOSTToDo_thenShouldReturn201() {
        JavalinTest.test(app, (server, client) -> {
            // given
            var todoRequest = """
                    {
                        "titulo": "%s",
                        "descricao": "%s",
                        "concluido": %b
                    }
                    """.formatted(DEFAULT_TITULO, DEFAULT_DESCRICAO, DEFAULT_CONCLUIDO);

            // when
            var response = client.post("/todo", todoRequest);
            var responseBody = objectMapper.readValue(response.body().string(), ToDoResponseDTO.class);

            // then
            assertEquals(201, response.code());
            assertEquals(CONTENT_TYPE, response.header("Content-Type"));
            assertToDo(responseBody);
        });
    }

    @Test
    void givenExistingToDo_whenCallsGETToDoById_ThenShouldReturn200() {
        JavalinTest.test(app, (server, client) -> {
            // given
            var todoRequest = """
                    {
                        "titulo": "%s",
                        "descricao": "%s",
                        "concluido": %b
                    }
                    """.formatted(DEFAULT_TITULO, DEFAULT_DESCRICAO, DEFAULT_CONCLUIDO);

            var createResponse = client.post("/todo", todoRequest);
            var createdToDo = objectMapper.readValue(createResponse.body().string(), ToDoResponseDTO.class);

            // when
            var getResponse = client.get("/todo/" + createdToDo.id());
            var responseBody = objectMapper.readValue(getResponse.body().string(), ToDoResponseDTO.class);

            // then
            assertEquals(200, getResponse.code());
            assertEquals(CONTENT_TYPE, getResponse.header("Content-Type"));
            assertToDoEquals(createdToDo, responseBody);
        });
    }

    private void assertToDo(ToDoResponseDTO todo) {
        assertNotNull(todo);
        assertNotNull(todo.id());
        assertNotNull(todo.dataCriacao());
        assertEquals(DEFAULT_TITULO, todo.titulo());
        assertEquals(DEFAULT_DESCRICAO, todo.descricao());
        assertEquals(DEFAULT_CONCLUIDO, todo.concluido());
    }

    private void assertToDoEquals(ToDoResponseDTO expected, ToDoResponseDTO actual) {
        assertNotNull(actual);
        assertEquals(expected.id(), actual.id());
        assertEquals(expected.dataCriacao(), actual.dataCriacao());
        assertEquals(expected.titulo(), actual.titulo());
        assertEquals(expected.descricao(), actual.descricao());
        assertEquals(expected.concluido(), actual.concluido());
    }
} 