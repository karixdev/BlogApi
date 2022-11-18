package com.github.karixdev.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseVote {

    @Id
    @SequenceGenerator(
            name = "vote_sequence",
            sequenceName = "vote_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vote_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id",
            name = "user_id",
            nullable = false
    )
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(
            name = "vote_type",
            nullable = false
    )
    private VoteType voteType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseVote baseVote = (BaseVote) o;
        return Objects.equals(id, baseVote.id) && Objects.equals(user, baseVote.user) && voteType == baseVote.voteType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, voteType);
    }
}
