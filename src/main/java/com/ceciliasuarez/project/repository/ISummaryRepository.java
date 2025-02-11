package com.ceciliasuarez.project.repository;

import com.ceciliasuarez.project.model.Summary;
import com.ceciliasuarez.project.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISummaryRepository extends JpaRepository<Summary, Long> {

    boolean existsByYearAndType(int year, Type type);
}
