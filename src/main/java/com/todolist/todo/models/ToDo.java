package com.todolist.todo.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "text", nullable = false, length = 150)
    private String text;

    public ToDo() {
    }

    public ToDo(Long userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDo)) return false;
        ToDo toDo = (ToDo) o;
        return id.equals(toDo.id) &&
                userId.equals(toDo.userId) &&
                text.equals(toDo.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, text);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
