package com.ceciliasuarez.project.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactForm {
    private String name;
    private String email;
    private String subject;
    private String message;
}
