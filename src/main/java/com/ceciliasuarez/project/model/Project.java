package com.ceciliasuarez.project.model;

import jakarta.persistence.*;
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
    private String site;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable(name = "project_skills", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @Column(nullable = false)
    private List<Skill> skills;

    /*GALERIA DE IMAGENES
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private List<Image> images;*/
}
