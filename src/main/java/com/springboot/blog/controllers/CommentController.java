package com.springboot.blog.controllers;

import com.springboot.blog.dtos.CommentDto;
import com.springboot.blog.exceptions.BlogApiException;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(
        name = "CRUD REST API's for Comment Resource"
)
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @Operation(
            summary = "Create Comment Rest API",
            description = "Create Comment REST API is used to save comment on a particular post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody CommentDto commentDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    @Operation(
            summary = "Get All Comments By PostId Rest API",
            description = "Get All Comments By PostId Rest API is used to get all comments of a particular post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public List<CommentDto> getAllCommentsBYPostId(@PathVariable("postId") Long postId) throws ResourceNotFoundException {
        return commentService.getAllComments(postId);
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    @Operation(
            summary = "Get Comment By PostId Rest API",
            description = "Get Comment By PostId & CommentId Rest API is used to get comment of a particular post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<CommentDto> getComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) throws ResourceNotFoundException, BlogApiException {
        return ResponseEntity.ok(commentService.getComment(postId, commentId));
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @Operation(
            summary = "Update Comment Rest API",
            description = "Update Comment By PostId & CommentId Rest API is used to update a particular comment of postId"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @Valid @RequestBody CommentDto commentDto) throws ResourceNotFoundException, BlogApiException {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @Operation(
            summary = "Delete Comment By PostId Rest API",
            description = "Get Comment By PostId Rest API is used to delete comment from a particular post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Successfully"
    )
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) throws ResourceNotFoundException, BlogApiException {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("comment with id "+commentId+" was deleted from Post with id "+postId+" Successfully",HttpStatus.OK);
    }
}
