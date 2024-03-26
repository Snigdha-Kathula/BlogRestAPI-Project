package com.springboot.blog.services.impl;

import com.springboot.blog.dtos.CommentDto;
import com.springboot.blog.exceptions.BlogApiException;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.mappers.DtoMapper;
import com.springboot.blog.models.Comment;
import com.springboot.blog.models.Post;
import com.springboot.blog.respositories.CommentRepository;
import com.springboot.blog.respositories.PostRepository;
import com.springboot.blog.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long id, CommentDto commentDto) throws ResourceNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post", "id", id));
        Comment comment = DtoMapper.convertDtoTOComment(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        Set<Comment> comments = post.getComments();
        comments.add(savedComment);
        postRepository.save(post);
        return DtoMapper.convertCommentTODto(savedComment);
    }

    @Override
    public List<CommentDto> getAllComments(Long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        List<Comment> comments = commentRepository.findAllByPost(post);
        List<CommentDto> commentDtos = comments.stream().map((comment)->DtoMapper.convertCommentTODto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getComment(Long postId, Long commentId) throws ResourceNotFoundException, BlogApiException {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
        if(!post.getId().equals(comment.getPost().getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment Does'nt belongs to Post");
        }
        return DtoMapper.convertCommentTODto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) throws ResourceNotFoundException, BlogApiException {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
        if(!post.getId().equals(comment.getPost().getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment Does'nt belongs to Post");
        }

        if(commentDto.getName() !=null)comment.setName(commentDto.getName());
        if(commentDto.getBody() !=null)comment.setBody(commentDto.getBody());
        if(commentDto.getEmail()!=null)comment.setEmail(commentDto.getEmail());
        Comment updatedComment = commentRepository.save(comment);
        return DtoMapper.convertCommentTODto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) throws BlogApiException, ResourceNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
        if(!post.getId().equals(comment.getPost().getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment Does'nt belongs to Post");
        }
        commentRepository.delete(comment);
    }


}
