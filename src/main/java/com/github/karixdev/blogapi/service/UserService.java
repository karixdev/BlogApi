package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.entity.User;
import com.github.karixdev.blogapi.entity.UserRole;
import com.github.karixdev.blogapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void enableUser(User user) {
        user.setIsEnabled(Boolean.TRUE);
        userRepository.save(user);
    }

    public boolean isUserAnAdmin(User user) {
        return user.getUserRole() == UserRole.ROLE_ADMIN;
    }

}
