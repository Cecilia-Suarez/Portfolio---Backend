package com.ceciliasuarez.project.model.translation;

import com.ceciliasuarez.project.model.Summary;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "summary_translations")
public class SummaryTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "summary_id", nullable = false)
    @JsonBackReference
    private Summary summary;

    @Column(nullable = false, length = 10)
    private String languageCode;
}
