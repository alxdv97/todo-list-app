package com.todolist.todo.security.jwt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jwt_blacklist")
public class JwtToken {

    @Id
    private String jwtToken;

    public JwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public JwtToken() {
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
