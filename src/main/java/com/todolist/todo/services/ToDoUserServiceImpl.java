package com.todolist.todo.services;

import com.todolist.todo.exceptions.UserAlreadyExistException;
import com.todolist.todo.models.ToDoUser;
import com.todolist.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoUserServiceImpl implements ToDoUserService {

    UserRepository repository;

    @Autowired
    public ToDoUserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ToDoUser register(ToDoUser user) throws UserAlreadyExistException {
        ToDoUser userForDB = new ToDoUser(user.getUserName(), user.getPassword());
        if (repository.findByUserName(userForDB.getUserName())==null){
            return repository.save(userForDB);
        } else {
            throw new UserAlreadyExistException("User already exist");
        }
    }

    @Override
    public List<ToDoUser> getAllUsers() {
        return (List<ToDoUser>) repository.findAll();
    }

    @Override
    public ToDoUser findByUserName(String name) {
        return repository.findByUserName(name);
    }

    @Override
    public ToDoUser findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
