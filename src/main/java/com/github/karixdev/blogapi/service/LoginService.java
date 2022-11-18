package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.dto.request.LoginRequest;
import com.github.karixdev.blogapi.dto.response.LoginResponse;
import com.github.karixdev.blogapi.dto.response.UserResponse;
import com.github.karixdev.blogapi.security.UserPrincipal;
import com.github.karixdev.blogapi.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
                .userResponse(new UserResponse(userPrincipal.getUser()))
                .build();
    }

}
