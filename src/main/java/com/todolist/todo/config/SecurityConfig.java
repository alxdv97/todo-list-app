package com.todolist.todo.config;

import com.todolist.todo.security.jwt.JwtConfigurer;
import com.todolist.todo.security.jwt.JwtTokenProvider;
import com.todolist.todo.security.jwt.blacklist.JwtBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/registration";

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtBlacklistService jwtBlacklistService;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, JwtBlacklistService jwtBlacklistService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                    .csrf().disable()//отключаем ненужное
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//отключаем контроль сессий
                .and()
                    .authorizeRequests()//все запросы - авторизированные
                    .antMatchers(LOGIN_ENDPOINT).permitAll()//разрешить всем страницу авторизации
                    .antMatchers(REGISTRATION_ENDPOINT).permitAll()//разрашеть всем смтраницу регистрации
                    .anyRequest().authenticated()//любой запрос от зарегистрированных
                .and()
                    .apply(new JwtConfigurer(jwtTokenProvider, jwtBlacklistService));//применить jwt-конфигурацию
    }

}
