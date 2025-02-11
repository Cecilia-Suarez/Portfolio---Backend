package com.ceciliasuarez.project.model.translation;

import com.ceciliasuarez.project.model.Skill;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "skill_translations")
public class SkillTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    @JsonBackReference
    private Skill skill;

    @Column(nullable = false, length = 10)
    private String languageCode;

    @Column(nullable = false)
    private String name;
}
