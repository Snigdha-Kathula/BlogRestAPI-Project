package com.springboot.blog.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have atleast 2 characters")
    @Schema(
            description = "Blog Post Tittle"
    )
    private String title;
    @NotEmpty
    @Size(min = 5, message = "Post description should have atleast 5 characters")
    @Schema(
            description = "Blog Post Description"
    )
    private String description;
    @NotEmpty
    @Schema(
            description = "Blog Content Description"
    )
    private String content;
    private Set<CommentDto> commentDtoSet;
    @Schema(
            description = "Blog CategoryId Description"
    )
    private Long categoryId;

}
