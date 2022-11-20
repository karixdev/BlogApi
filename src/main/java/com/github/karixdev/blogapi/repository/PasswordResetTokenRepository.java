package com.github.karixdev.blogapi.repository;

import com.github.karixdev.blogapi.entity.PasswordResetToken;
import com.github.karixdev.blogapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    @Query("""
            SELECT token
            FROM PasswordResetToken token
            WHERE token.user = :user
            AND token.resetAt IS NULL
            ORDER BY token.createdAt DESC
            """)
    List<PasswordResetToken> findNonResetSortDesc(@Param("user") User user);

    @Query("""
            SELECT resetToken
            FROM PasswordResetToken resetToken
            WHERE resetToken.token = :token
            """)
    Optional<PasswordResetToken> findByToken(@Param("token") String token);
}
