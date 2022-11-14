package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.security.CurrentUser;
import com.github.karixdev.blogapi.security.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public UserPrincipal hello(@CurrentUser UserPrincipal userPrincipal) {
        return userPrincipal;
    }

}
