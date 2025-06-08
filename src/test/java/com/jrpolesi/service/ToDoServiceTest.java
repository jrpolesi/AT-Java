package com.jrpolesi.service;

import com.jrpolesi.dto.ToDoRequestDTO;
import com.jrpolesi.dto.ToDoResponseDTO;
import com.jrpolesi.model.ToDo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {
    @Mock
    private IToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    @Test
    void givenValidToDoRequest_whenCreateToDo_thenShouldCreateAndReturnToDo() {
        // given
        var request = new ToDoRequestDTO("Titulo", "Descrição", true);

        // when
        var response = toDoService.createToDo(request);

        // then
        assertNotNull(response);
        assertEquals(request.titulo(), response.titulo());
        assertEquals(request.descricao(), response.descricao());
        assertEquals(request.concluido(), response.concluido());
        assertNotNull(response.id());
        assertNotNull(response.dataCriacao());
        verify(toDoRepository, times(1)).save(eq(ToDo.with(
                request.titulo(),
                request.descricao(),
                request.concluido()
        )));
    }

    @Test
    void givenExistingToDos_whenFindAllToDos_thenShouldReturnAllToDos() {
        // given
        var todo1 = ToDo.with("ToDo 1", "Description 1", false);
        var todo2 = ToDo.with("ToDo 2", "Description 2", true);
        when(toDoRepository.findAll()).thenReturn(List.of(todo1, todo2));

        // when
        var response = toDoService.findAllToDos();

        // then
        assertNotNull(response);
        assertEquals(2, response.size());

        assertToDo(todo1, response.get(0));
        assertToDo(todo2, response.get(1));

        verify(toDoRepository, times(1)).findAll();
    }

    @Test
    void givenExistingToDo_whenFindOneById_thenShouldReturnToDo() {
        // given
        var todo = ToDo.with("ToDo 1", "Description 1", false);
        var uuid = todo.getId();
        when(toDoRepository.findOneById(uuid)).thenReturn(todo);

        // when
        var response = toDoService.findOneById(uuid.toString());

        // then
        assertToDo(todo, response);

        verify(toDoRepository, times(1)).findOneById(uuid);
    }

    @Test
    void givenNonExistingId_whenFindOneById_thenShouldReturnNull() {
        // given
        var uuid = UUID.randomUUID();
        when(toDoRepository.findOneById(uuid)).thenReturn(null);

        // when
        var response = toDoService.findOneById(uuid.toString());

        // then
        assertNull(response);
        verify(toDoRepository, times(1)).findOneById(uuid);
    }

    @Test
    void givenInvalidUUID_whenFindOneById_thenShouldThrowIllegalArgumentException() {
        // given
        var invalidId = "invalid-uuid";

        // when
        final var actualException = assertThrows(IllegalArgumentException.class, () -> {
            toDoService.findOneById(invalidId);
        });

        // then
        assertEquals("Invalid UUID string: " + invalidId, actualException.getMessage());
    }

    private void assertToDo(ToDo expected, ToDoResponseDTO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId().toString(), actual.id());
        assertEquals(expected.getTitle(), actual.titulo());
        assertEquals(expected.getDescription(), actual.descricao());
        assertEquals(expected.isCompleted(), actual.concluido());
        assertNotNull(actual.dataCriacao());
    }
} 