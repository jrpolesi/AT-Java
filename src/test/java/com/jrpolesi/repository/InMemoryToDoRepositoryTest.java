package com.jrpolesi.repository;

import com.jrpolesi.model.ToDo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryToDoRepositoryTest {
    private static List<ToDo> DEFAULT_TODOS = List.of(
            ToDo.with("Default ToDo 1", "Default Description 1", false),
            ToDo.with("Default ToDo 2", "Default Description 2", true)
    );

    private InMemoryToDoRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new InMemoryToDoRepository(DEFAULT_TODOS);
    }

    @Test
    void givenValidToDo_whenSave_thenShouldSaveSuccessfully() {
        // given
        var toDo = ToDo.with("Test ToDo", "Test Description", false);

        // when
        final var savedToDo = repository.save(toDo);

        // then
        assertNotNull(savedToDo);
        assertEquals(toDo.getId(), savedToDo.getId());
        assertEquals(toDo.getTitle(), savedToDo.getTitle());
        assertEquals(toDo.getDescription(), savedToDo.getDescription());
        assertEquals(toDo.isCompleted(), savedToDo.isCompleted());
        assertEquals(toDo.getCreatedAt(), savedToDo.getCreatedAt());
    }

    @Test
    void givenExistingToDos_whenFindAll_thenShouldReturnAllToDos() {
        // when
        var result = repository.findAll();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(DEFAULT_TODOS.get(0)));
        assertTrue(result.contains(DEFAULT_TODOS.get(1)));
    }

    @Test
    void givenExistingToDo_whenFindOneById_thenShouldReturnToDo() {
        // when
        var result = repository.findOneById(DEFAULT_TODOS.get(0).getId());

        // then
        assertNotNull(result);
        assertEquals(DEFAULT_TODOS.get(0), result);
    }

    @Test
    void givenNonExistingId_whenFindOneById_thenShouldReturnNull() {
        // given
        var nonExistingId = UUID.randomUUID();

        // when
        var result = repository.findOneById(nonExistingId);

        // then
        assertNull(result);
    }
} 