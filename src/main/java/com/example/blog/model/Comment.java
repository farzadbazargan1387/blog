package com.example.blog.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String content;
    private String authorName;
    private String authorEmail;
    private boolean approved = false;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private Post post;
}
