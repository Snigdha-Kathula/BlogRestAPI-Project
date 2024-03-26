package com.springboot.blog.services;

import com.springboot.blog.dtos.CommentDto;
import com.springboot.blog.exceptions.BlogApiException;
import com.springboot.blog.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long id, CommentDto commentDto) throws ResourceNotFoundException;
    List<CommentDto> getAllComments(Long postId) throws ResourceNotFoundException;
    CommentDto getComment(Long postId, Long commentId) throws ResourceNotFoundException, BlogApiException;
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) throws ResourceNotFoundException, BlogApiException;
    void deleteComment(Long postId, Long commentId) throws BlogApiException, ResourceNotFoundException;
}
