package com.ceciliasuarez.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    @Pattern(
            regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})(/[\\w-]*)*$",
            message = "The site must be in a valid web format, for example: www.example.com"
    )
    private String site;

    @Column(nullable = false)
    @Pattern(
            regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})(/[\\w-]*)*$",
            message = "The site must be in a valid web format, for example: www.example.com"
    )
    private String repository;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable(name = "project_skills", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @Column(nullable = false)
    private List<Skill> skills;

    @Lob
    @ElementCollection
    private List<String> images;

}
