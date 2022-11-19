package com.github.karixdev.blogapi.repository;

import com.github.karixdev.blogapi.entity.BlogPostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostVoteRepository extends JpaRepository<BlogPostVote, Long> {
}
