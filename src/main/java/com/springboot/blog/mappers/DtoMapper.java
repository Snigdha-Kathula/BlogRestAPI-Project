package com.springboot.blog.mappers;

import com.springboot.blog.dtos.CommentDto;
import com.springboot.blog.dtos.PostDto;
import com.springboot.blog.models.Comment;
import com.springboot.blog.models.Post;

import java.util.Set;
import java.util.stream.Collectors;

public class DtoMapper {
    public static Comment convertDtoTOComment(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
    public static CommentDto convertCommentTODto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        commentDto.setId(comment.getId());
        return commentDto;
    }
    public static PostDto convertPostToPostDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        Set<CommentDto> dtos = post.getComments().stream().map(comment -> convertCommentTODto(comment)).collect(Collectors.toSet());
        postDto.setCommentDtoSet(dtos);
         return postDto;
    }
    public static Post convertPostDtoToPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
