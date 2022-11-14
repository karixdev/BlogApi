package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.dto.LoginRequest;
import com.github.karixdev.blogapi.dto.LoginResponse;
import com.github.karixdev.blogapi.dto.UserResponse;
import com.github.karixdev.blogapi.entity.UserRole;
import com.github.karixdev.blogapi.security.UserPrincipal;
import com.github.karixdev.blogapi.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtProvider.generateToken(authentication);

        return LoginResponse.builder()
                .accessToken(token)
                .userResponse(userService.mapUserToUserResponse(userPrincipal.getUser()))
                .build();
    }

}
