package com.todolist.todo.services;

import com.todolist.todo.models.ToDo;
import com.todolist.todo.exceptions.ToDoNotFoundException;
import com.todolist.todo.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository repository;

    @Autowired
    public ToDoServiceImpl(ToDoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ToDo create(ToDo todo) {
        ToDo toDoForDB = new ToDo(todo.getUserId(), todo.getText());
        return repository.save(toDoForDB);
    }

    @Override
    public List<ToDo> getAll() {
        return (List<ToDo>) repository.findAll();
    }

    @Override
    public void update(Long id, ToDo todo) throws ToDoNotFoundException {
        ToDo todoFromDB = repository.findById(id)
                .orElseThrow(ToDoNotFoundException::new);
        if (todo.getText() != null) {
            todoFromDB.setText(todo.getText());
            repository.save(todoFromDB);
        }
    }

    @Override
    public void delete(Long id) throws ToDoNotFoundException {
        if (repository.findById(id).isEmpty()) {
            throw new ToDoNotFoundException();
        }
        repository.deleteById(id);
    }
}
