package com.github.karixdev.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PasswordResetToken")
@Table(
        name = "password_reset_token",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "password_reset_token_token_unique",
                        columnNames = "token"
                )
        }
)
public class PasswordResetToken {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "password_reset_token_sequence"
    )
    @SequenceGenerator(
            name = "password_reset_token_sequence",
            sequenceName = "password_reset_token_sequence",
            allocationSize = 1
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(
            name = "token",
            nullable = false
    )
    private String token;

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id",
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "password_rest_token_user_id_fk"
            )
    )
    private User user;

    @Column(
            name = "expires_at",
            nullable = false
    )
    private LocalDateTime expiresAt;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "reset_at",
            nullable = false
    )
    private LocalDateTime resetAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetToken token1 = (PasswordResetToken) o;
        return Objects.equals(id, token1.id) && Objects.equals(token, token1.token) && Objects.equals(user, token1.user) && Objects.equals(expiresAt, token1.expiresAt) && Objects.equals(createdAt, token1.createdAt) && Objects.equals(resetAt, token1.resetAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, expiresAt, createdAt, resetAt);
    }
}
