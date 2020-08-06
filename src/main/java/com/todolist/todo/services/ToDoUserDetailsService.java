package com.todolist.todo.services;

import com.todolist.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.todolist.todo.entities.ToDoUser;

@Service
public class ToDoUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public ToDoUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ToDoUser myUser = repository.findByUserName(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        UserDetails user = User.builder()
                .username(myUser.getUserName())
                .password(myUser.getPassword())
                .roles("user")
                .build();
        return user;
    }
}
