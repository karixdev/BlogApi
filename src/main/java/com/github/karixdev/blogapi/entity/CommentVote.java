package com.github.karixdev.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "CommentVote")
@Table(name = "comment_vote")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentVote extends BaseVote {
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id",
            name = "comment_id",
            foreignKey = @ForeignKey(
                    name = "comment_vote_comment_id_fk"
            ),
            nullable = false
    )
    private Comment comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommentVote that = (CommentVote) o;
        return Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), comment);
    }
}
