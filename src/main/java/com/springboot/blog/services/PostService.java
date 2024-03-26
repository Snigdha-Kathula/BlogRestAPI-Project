package com.springboot.blog.services;

import com.springboot.blog.dtos.PostDto;
import com.springboot.blog.dtos.PostResponse;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.models.Post;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);
//    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

//    public List<PostDto> getAllPosts(int pageNo, int pageSize);

    //    public List<PostDto> getAllPosts();
    public PostDto getPostById(Long id) throws ResourceNotFoundException;
    public PostDto updatePostById(PostDto postDto, Long id) throws ResourceNotFoundException;
    public void deletePostById(Long id) throws ResourceNotFoundException;
    List<Post> getPostByCategoryId(Long categoryId) throws ResourceNotFoundException;
}
