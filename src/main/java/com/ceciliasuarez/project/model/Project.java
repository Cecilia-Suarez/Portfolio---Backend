package com.ceciliasuarez.project.model;

import com.ceciliasuarez.project.model.translation.ProjectTranslation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTranslation> translations;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    @Pattern(
            regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?$",
            message = "The site must be in a valid web format, for example: https://www.example.com"
    )
    private String site;

    @Column(nullable = false)
    @Pattern(
            regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?$",
            message = "The site must be in a valid web format, for example: https://www.example.com"
    )
    private String repository;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable(name = "project_skills", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @Column(nullable = false)
    private List<Skill> skills;

    @ElementCollection
    @Size(min = 1, message = "At least one image URL must be provided.")
    private List<String> images;

}
