package com.todolist.todo.services;

import com.todolist.todo.exceptions.UserAlreadyExistException;
import com.todolist.todo.models.ToDoUser;

import java.util.List;

public interface ToDoUserService {
    ToDoUser register(ToDoUser user) throws UserAlreadyExistException;

    List<ToDoUser> getAllUsers();

    ToDoUser findByUserName(String name);

    ToDoUser findById(Long id);

    void delete(Long id);
}
