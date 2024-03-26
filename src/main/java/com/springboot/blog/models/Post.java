package com.springboot.blog.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String title;
     private String description;
     private String content;
     @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = false)
     private Set<Comment> comments =new HashSet<>();
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "category_id")
     private Category category;

}
