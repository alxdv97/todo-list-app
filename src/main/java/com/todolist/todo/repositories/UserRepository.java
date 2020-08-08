package com.todolist.todo.repositories;

import com.todolist.todo.models.ToDoUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<ToDoUser, Long> {
    ToDoUser findByUserName(String userName);
}
