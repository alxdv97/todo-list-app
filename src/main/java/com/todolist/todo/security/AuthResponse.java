package com.todolist.todo.security;

public class AuthResponse {
    private String userName;
    private String token;

    public AuthResponse(String userName, String jwtToken) {
        this.userName = userName;
        this.token = jwtToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
