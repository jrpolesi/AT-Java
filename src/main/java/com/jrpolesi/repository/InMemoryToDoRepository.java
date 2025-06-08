package com.jrpolesi.repository;

import com.jrpolesi.model.ToDo;
import com.jrpolesi.service.IToDoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryToDoRepository implements IToDoRepository {
    private final List<ToDo> toDoList = new ArrayList<>();

    @Override
    public void save(ToDo toDo) {
        toDoList.add(toDo);
    }

    @Override
    public List<ToDo> findAll() {
        return toDoList;
    }

    @Override
    public ToDo findOneById(UUID id) {
        return toDoList.stream()
                .filter(toDo -> toDo.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
