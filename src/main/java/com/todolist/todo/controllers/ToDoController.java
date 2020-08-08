package com.todolist.todo.controllers;

import com.todolist.todo.models.ToDo;
import com.todolist.todo.exceptions.ToDoNotFoundException;
import com.todolist.todo.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class ToDoController {

    private final ToDoService service;

    @Autowired
    public ToDoController(ToDoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<ToDo>> getToDoList() {
        List<ToDo> all = service.getAll();
        if (!all.isEmpty()) {
            return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singleton(new ToDo()), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ToDo addToDo(@RequestBody ToDo todo) {
        return service.create(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editToDo(@PathVariable("id") String id,
                                           @RequestBody ToDo todo) {
        try {
            service.update(Long.parseLong(id), todo);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (ToDoNotFoundException e) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteToDo(@PathVariable("id") String id) {
        try {
            service.delete(Long.parseLong(id));
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (ToDoNotFoundException e) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
    }
}
