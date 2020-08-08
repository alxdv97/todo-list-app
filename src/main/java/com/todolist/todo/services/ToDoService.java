package com.todolist.todo.services;

import com.todolist.todo.models.ToDo;
import com.todolist.todo.exceptions.ToDoNotFoundException;

import java.util.List;

public interface ToDoService {
    ToDo create(ToDo todo);

    List<ToDo> getAll();

    void update(Long id, ToDo todo) throws ToDoNotFoundException;

    void delete(Long id) throws ToDoNotFoundException;
}
