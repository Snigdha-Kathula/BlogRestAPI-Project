package com.springboot.blog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class CommentDto {
    private Long id;
    @NotEmpty(message = "name should not be null or empty")
    private String name;
    @NotEmpty(message = "Email should not be null or Empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 5, message = "Comment body must be minimum 5 charaters")
    private String body;
}
