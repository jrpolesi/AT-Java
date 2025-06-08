package com.jrpolesi.service;

import com.jrpolesi.dto.ToDoRequestDTO;
import com.jrpolesi.dto.ToDoResponseDTO;
import com.jrpolesi.model.ToDo;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class ToDoService {
    private final IToDoRepository toDoRepository;

    public ToDoService(IToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDoResponseDTO createToDo(ToDoRequestDTO aTodo) {
        final var todo = ToDo.with(aTodo.titulo(), aTodo.descricao(), aTodo.concluido());

        toDoRepository.save(todo);

        return toResponseDTO(todo);
    }

    public List<ToDoResponseDTO> findAllToDos() {
        return toDoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ToDoResponseDTO findOneById(String id) {
        final var todo = toDoRepository.findOneById(java.util.UUID.fromString(id));

        return Objects.isNull(todo) ? null : toResponseDTO(todo);
    }

    public ToDoResponseDTO toResponseDTO(ToDo todo) {
        return new ToDoResponseDTO(
                todo.getId().toString(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
    }
}
