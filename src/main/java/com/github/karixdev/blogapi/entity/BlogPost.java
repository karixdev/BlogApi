package com.github.karixdev.blogapi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "BlogPost")
@Table(name = "blog_post")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogPost {

    @Id
    @SequenceGenerator(
            name = "blog_post_sequence",
            sequenceName = "blog_post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "blog_post_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Lob
    @Column(
            name = "content",
            nullable = false
    )
    private String content;

    @ManyToOne
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "blog_post_author_id_fk"
            ),
            nullable = false
    )
    private User author;

    @OneToMany(mappedBy = "blogPost", orphanRemoval = true)
    @ToString.Exclude
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "blogPost", orphanRemoval = true)
    private Set<BlogPostVote> votes = new LinkedHashSet<>();

    public BlogPost(
            String title,
            String content,
            User author
    ) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return Objects.equals(id, blogPost.id) && Objects.equals(title, blogPost.title) && Objects.equals(content, blogPost.content) && Objects.equals(author, blogPost.author) && Objects.equals(comments, blogPost.comments) && Objects.equals(votes, blogPost.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, author, comments, votes);
    }
}
