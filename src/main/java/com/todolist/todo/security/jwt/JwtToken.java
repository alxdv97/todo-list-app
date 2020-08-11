package com.todolist.todo.security.jwt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JwtToken)) return false;
        JwtToken jwtToken1 = (JwtToken) o;
        return jwtToken.equals(jwtToken1.jwtToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwtToken);
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
