package com.todolist.todo.services;

import com.todolist.todo.entities.ToDo;
import com.todolist.todo.exceptions.ToDoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ToDoService {
    ToDo create(ToDo todo);

    List<ToDo> getAll();

    boolean update(Long id, ToDo todo) throws ToDoNotFoundException;

    void delete(Long id) throws ToDoNotFoundException;
}
