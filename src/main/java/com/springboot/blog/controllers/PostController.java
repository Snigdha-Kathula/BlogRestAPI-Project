package com.springboot.blog.controllers;

import com.springboot.blog.dtos.PostDto;
import com.springboot.blog.dtos.PostResponse;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.models.Post;
import com.springboot.blog.services.PostService;
import com.springboot.blog.utils.AppConstants;
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
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST API's for Post Resource"
)
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @Operation(
            summary = "Create Post Rest API",
            description = "Create Post REST API is used to save post into the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto postDto1 = postService.createPost(postDto);
        ResponseEntity<PostDto> response = new ResponseEntity<>(postDto1, HttpStatus.CREATED);
        return response;
    }


    @GetMapping
    @Operation(
            summary = "Get All Posts Rest API",
            description = "Get All Posts REST API is used to fetch all posts from the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy
     ){

        return postService.getAllPosts(pageNo, pageSize, sortBy);
    }

//    @GetMapping
//    public PostResponse getAllPosts(
//            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
//    ){
//
//        return postService.getAllPosts(pageNo, pageSize);
//    }


//    @GetMapping
//    public List<PostDto> getAllPosts(
//            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
//    ){
//
//        return postService.getAllPosts(pageNo, pageSize);
//    }


//    @GetMapping
//    public List<PostDto> getAllPosts(){
//        return postService.getAllPosts();
//    }
    @GetMapping("{id}")
    @Operation(
            summary = "Get Post By Id Rest API",
            description = "Get Post By Id REST API is used to get single post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @GetMapping("{id}")
    @Operation(
            summary = "Update Post Rest API",
            description = "Update Post REST API is used to update particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto,@PathVariable("id") Long id) throws ResourceNotFoundException {
        PostDto postDto1 = postService.updatePostById(postDto, id);
        return new ResponseEntity<>(postDto1, HttpStatus.OK);
//        return ResponseEntity.ok(postService.updatePostById(postDto, id));
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"

    )
    @GetMapping("{id}")
    @Operation(
            summary = "Delete Post By Id Rest API",
            description = "Delete Post By Id REST API is used to delete particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        postService.deletePostById(id);
        return ResponseEntity.ok("Post with id :"+id+" was deleted Successfully");
    }
    @GetMapping("/category/{id}")
    @Operation(
            summary = "Get Post By Category Id Rest API",
            description = "Get Post By Category Id Rest API is used to get a post by category id"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<List<Post>> getPostByCategory(@PathVariable("id") Long categoryId) throws ResourceNotFoundException {
        List<Post> posts = postService.getPostByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }
}
