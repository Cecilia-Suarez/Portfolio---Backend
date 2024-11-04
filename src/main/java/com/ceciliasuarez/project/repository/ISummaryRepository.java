package com.ceciliasuarez.project.repository;

import com.ceciliasuarez.project.model.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISummaryRepository extends JpaRepository<Summary, Long> {
}
