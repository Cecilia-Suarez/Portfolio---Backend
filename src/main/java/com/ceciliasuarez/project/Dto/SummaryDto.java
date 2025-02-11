package com.ceciliasuarez.project.Dto;

import com.ceciliasuarez.project.model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SummaryDto {
    private Long id;
    private String name;
    private String location;
    private int year;
    private Type type;
}
