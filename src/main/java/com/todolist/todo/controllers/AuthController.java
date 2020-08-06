package com.todolist.todo.controllers;

import com.todolist.todo.security.AuthRequest;
import com.todolist.todo.security.AuthResponse;
import com.todolist.todo.security.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtTokenUtil;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
            System.out.println(authentication);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Имя или пароль неправильны", e);
        }

        final String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        System.out.println(jwt);

        return new AuthResponse(jwt);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void invalidateToken() {
        //todo how to invalidate token&&?
    }
}
