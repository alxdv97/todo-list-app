package com.todolist.todo.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
public class ToDoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String userName;
    @NotNull
    private String password;

    public ToDoUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
