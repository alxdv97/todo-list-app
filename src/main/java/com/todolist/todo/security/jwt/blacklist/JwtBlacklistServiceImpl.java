package com.todolist.todo.security.jwt.blacklist;

import com.todolist.todo.security.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {


    private final JwtBlacklistRepository blacklist;

    @Autowired
    public JwtBlacklistServiceImpl(JwtBlacklistRepository blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean isInBlacklist(String jwtToken) {
        return blacklist.findById(jwtToken).isPresent();
    }

    @Override
    public void storeInBlackList(String jwtToken) {
        blacklist.save(new JwtToken(jwtToken));
    }
}
