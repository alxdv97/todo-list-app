package com.todolist.todo.security.jwt.blacklist;

import org.springframework.stereotype.Service;

public interface JwtBlacklistService {
    boolean isInBlacklist(String jwtToken);
    void storeInBlackList(String jwtToken);
}
