package com.todolist.todo.controllers;

import com.todolist.todo.exceptions.UserAlreadyExistException;
import com.todolist.todo.models.ToDoUser;
import com.todolist.todo.security.AuthRequest;
import com.todolist.todo.security.AuthResponse;
import com.todolist.todo.security.jwt.JwtTokenProvider;
import com.todolist.todo.security.jwt.blacklist.JwtBlacklistService;
import com.todolist.todo.services.ToDoUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

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
            ToDoUser user = userService.findByUserName(request.getUserName());
            if (user == null) {
                throw new UsernameNotFoundException("User with username: "
                        + user.getUserName() + " not found");
            }
            String token = jwtTokenProvider.createToken(user.getUserName());
            return new ResponseEntity<>((new AuthResponse(user.getUserName(), token)), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new AuthResponse(null, null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("registration")
    public ResponseEntity<ToDoUser> registration(@RequestBody ToDoUser user) {
        try {
            return ResponseEntity.ok(userService.register(user));
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("signout")
    public ResponseEntity signout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        blacklistService.storeInBlackList(token);
        SecurityContextHolder.getContext()
                .getAuthentication().setAuthenticated(false);
        return new ResponseEntity(HttpStatus.OK);
    }
}
