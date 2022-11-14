package com.github.karixdev.blogapi.security;

import com.github.karixdev.blogapi.entity.User;
import com.github.karixdev.blogapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(
                            String.format("User with email: %s not found", username)
                    );
                });

        return new UserPrincipal(user);
    }
}
