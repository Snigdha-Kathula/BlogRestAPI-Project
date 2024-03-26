package com.springboot.blog.respositories;

import com.springboot.blog.models.Comment;
import com.springboot.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByPost(Post post);
}
