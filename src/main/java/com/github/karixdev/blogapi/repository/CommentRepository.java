package com.github.karixdev.blogapi.repository;

import com.github.karixdev.blogapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}