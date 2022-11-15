package com.github.karixdev.blogapi.repository;

import com.github.karixdev.blogapi.entity.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    @Query(value = """
                SELECT blogPost
                FROM BlogPost blogPost
                LEFT JOIN FETCH blogPost.author
                ORDER BY blogPost.id
                ASC
            """,
            countQuery = """
                SELECT COUNT(blogPost)
                FROM BlogPost blogpost
            """
    )
    Page<BlogPost> findAllBlogPost(Pageable pageable);

}
