package com.todolist.todo.controllers;

import com.todolist.todo.models.ToDoUser;
import com.todolist.todo.security.AuthRequest;
import com.todolist.todo.security.AuthResponse;
import com.todolist.todo.security.jwt.JwtTokenProvider;
import com.todolist.todo.security.jwt.blacklist.JwtBlacklistService;
import com.todolist.todo.services.ToDoUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final ToDoUserService userService;

    private final JwtBlacklistService blacklistService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          ToDoUserService userService,
                          JwtBlacklistService blacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.blacklistService = blacklistService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            String userName = request.getUserName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,
                    request.getPassword()));
            ToDoUser user = userService.findByUserName(userName);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + userName + " not found");
            }

            String token = jwtTokenProvider.createToken(userName);

            return ResponseEntity.ok(new AuthResponse(userName, token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("registration")
    public ResponseEntity<ToDoUser> registration(@RequestBody ToDoUser user) {
        return ResponseEntity.ok(userService.register(user));
    }

    //TODO: how to get info about user in @GET??
//    @GetMapping("signout")
//    public void signout(){
//
//    }

    @PostMapping("signout")
    public ResponseEntity<AuthResponse> logout(@RequestBody AuthRequest request) {

        String token = jwtTokenProvider.createToken(request.getUserName());
        blacklistService.storeInBlackList(token);

        return ResponseEntity.ok(new AuthResponse(request.getUserName(), token));
    }

}
