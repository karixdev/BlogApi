package com.github.karixdev.blogapi.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(
                            String.format("User with email: %s not found", username)
                    );
                });
    }

    public void enableUser(User user) {
        user.setIsEnabled(Boolean.TRUE);
        userRepository.save(user);
    }
}
