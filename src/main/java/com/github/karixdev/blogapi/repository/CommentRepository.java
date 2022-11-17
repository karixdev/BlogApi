package com.github.karixdev.blogapi.repository;

import com.github.karixdev.blogapi.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
            value = """
                    SELECT comment
                    FROM Comment comment
                    WHERE comment.blogPost.id = :blogPostId
                    """,
            countQuery = """
                    SELECT COUNT(comment)
                    FROM Comment comment
                    WHERE comment.blogPost.id = :blogPostId
                    """
    )
    Page<Comment> findByBlogPostId(@Param("blogPostId") Long blogPostId, Pageable pageable);

}