package com.example.blog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique=true)
    private String slug;
}
