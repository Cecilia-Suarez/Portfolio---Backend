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
    @JoinTable(name = "skills", joinColumns = @JoinColumn(name = "id_project"), inverseJoinColumns = @JoinColumn(name = "id_skill"))
    @Column(nullable = false)
    private List<Skill> skills;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private List<String> images;

}
