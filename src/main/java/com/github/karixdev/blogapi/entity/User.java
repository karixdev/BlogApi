package com.github.karixdev.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "User")
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_email_unique",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "user_role",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(
            name = "is_enabled",
            nullable = false
    )
    private Boolean isEnabled;

    @OneToMany(
            mappedBy = "author",
            orphanRemoval = true
    )
    @ToString.Exclude
    @Builder.Default
    private Set<BlogPost> blogPosts = new HashSet<>();

    public User(
            String email,
            String firstName,
            String lastName,
            String password,
            UserRole userRole,
            Boolean isEnabled
    ) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRole = userRole;
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && userRole == user.userRole && Objects.equals(isEnabled, user.isEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, password, userRole, isEnabled);
    }
}
