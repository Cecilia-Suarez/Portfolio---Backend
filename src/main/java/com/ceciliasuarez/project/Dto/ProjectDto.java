package com.ceciliasuarez.project.Dto;

import com.ceciliasuarez.project.model.Category;
import com.ceciliasuarez.project.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private int year;
    private String site;
    private String repository;
    private Category category;
    private List<Skill> skills;
    private List<String> images;

}
