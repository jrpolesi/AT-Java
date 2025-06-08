package com.jrpolesi.repository;

import com.jrpolesi.model.ToDo;
import com.jrpolesi.service.IToDoRepository;

import java.util.ArrayList;
import java.util.List;

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
}
