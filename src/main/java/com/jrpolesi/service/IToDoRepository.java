package com.jrpolesi.service;

import com.jrpolesi.model.ToDo;

import java.util.List;

public interface IToDoRepository {
    ToDo save(ToDo toDo);

    List<ToDo> findAll();

    ToDo findOneById(java.util.UUID id);
}
