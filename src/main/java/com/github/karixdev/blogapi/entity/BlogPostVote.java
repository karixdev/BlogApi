package com.github.karixdev.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "BlogPostVote")
@Table(name = "blog_post_vote")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostVote extends BaseVote {
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id",
            name = "blog_post_id",
            foreignKey = @ForeignKey(
                    name = "blog_post_vote_blog_post_id_fk"
            ),
            nullable = false
    )
    private BlogPost blogPost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BlogPostVote that = (BlogPostVote) o;
        return Objects.equals(blogPost, that.blogPost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), blogPost);
    }
}
