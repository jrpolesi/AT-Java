package com.jrpolesi.service;

import com.jrpolesi.model.ToDo;

import java.util.List;

public interface IToDoRepository {
    void save(ToDo toDo);
    List<ToDo> findAll();
}
