package com.github.karixdev.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Comment")
@Table(name = "comment")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    @Setter(AccessLevel.NONE)
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id",
            name = "blog_post_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "blog_post_id_fk"
            )
    )
    private BlogPost blogPost;

    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id",
            name = "author_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "comment_author_id_fk"
            )
    )
    private User author;

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private Set<CommentVote> votes = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(content, comment.content) && Objects.equals(blogPost, comment.blogPost) && Objects.equals(author, comment.author) && Objects.equals(votes, comment.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, blogPost, author, votes);
    }
}
