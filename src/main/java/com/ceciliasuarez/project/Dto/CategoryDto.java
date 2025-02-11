package com.ceciliasuarez.project.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String language;
}
