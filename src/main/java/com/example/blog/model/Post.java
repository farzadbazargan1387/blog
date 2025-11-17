package com.example.blog.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(unique=true)
    private String slug;
    @Column(columnDefinition="TEXT")
    private String content;
    @Column(columnDefinition="TEXT")
    private String excerpt;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DRAFT;

    @ManyToOne
    private User author;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
    private long views = 0;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(name="post_tags",
            joinColumns = @JoinColumn(name="post_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id"))
    private Set<Tag> tags;

    public static enum Status { DRAFT, PUBLISHED }
}
