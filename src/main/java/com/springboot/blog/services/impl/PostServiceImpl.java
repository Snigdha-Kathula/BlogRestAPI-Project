package com.springboot.blog.services.impl;

import com.springboot.blog.dtos.PostDto;
import com.springboot.blog.dtos.PostResponse;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.mappers.DtoMapper;
import com.springboot.blog.models.Category;
import com.springboot.blog.models.Post;
import com.springboot.blog.respositories.CategoryRepository;
import com.springboot.blog.respositories.PostRepository;
import com.springboot.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
//    private ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto){
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow();
        Post post = DtoMapper.convertPostDtoToPost(postDto);
        post.setCategory(category);
        Post postResponse = postRepository.save(post);

        PostDto dto = DtoMapper.convertPostToPostDto(postResponse);
        dto.setCategoryId(category.getId());
        return dto;
    }

//    @Override
//    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortDir).ascending():Sort.by(sortDir).descending();
//        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
//        Page<Post> posts = postRepository.findAll(pageable);
//        List<Post> postList = posts.getContent();
//        List<PostDto> content = postList.stream().map(this::convertPostToPostDto).collect(Collectors.toList());
//        PostResponse postResponse = new PostResponse();
//        postResponse.setContent(content);
//        postResponse.setPageNo(posts.getNumber());
//        postResponse.setPageSize(posts.getSize());
//        postResponse.setTotalElements(posts.getTotalElements());
//        postResponse.setTotalPages(posts.getTotalPages());
//        postResponse.setLast(posts.isLast());
//        return postResponse;
//    }
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDto> content = postList.stream().map((post)->DtoMapper.convertPostToPostDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

//    @Override
//    public List<PostDto> getAllPosts(int pageNo, int pageSize) {
//        PageRequest pageable = PageRequest.of(pageNo, pageSize);
//
//        Page<Post> posts = postRepository.findAll(pageable);
//        List<Post> postList = posts.getContent();
//        return postList.stream().map((post)->convertPostToPostDto(post) ).collect(Collectors.toList());
//    }

//    @Override
//    public List<PostDto> getAllPosts() {
//         List<Post> posts = postRepository.findAll();
//         return posts.stream().map((post)->convertPostToPostDto(post) ).collect(Collectors.toList());
//    }

    @Override
    public PostDto getPostById(Long id) throws ResourceNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
        return DtoMapper.convertPostToPostDto(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, Long id) throws ResourceNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
        Category category = categoryRepository.findById(postDto.getCategoryId())
                        .orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post post1 = postRepository.save(post);
        PostDto postDto1 = DtoMapper.convertPostToPostDto(post1);
        postDto1.setCategoryId(category.getId());
        return postDto1;
    }

    @Override
    public void deletePostById(Long id) throws ResourceNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostByCategoryId(Long categoryId) throws ResourceNotFoundException {

        categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts;
    }


}
