package com.github.karixdev.blogapi.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository {
    @Query("""
            SELECT user
            FROM User user
            WHERE user.email = :email
            """)
    Optional<User> findByEmail(@Param("email") String email);
}
