package com.todolist.todo.security.jwt.blacklist;

import com.todolist.todo.security.jwt.JwtToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtBlacklistRepository extends CrudRepository<JwtToken, String> {
}
