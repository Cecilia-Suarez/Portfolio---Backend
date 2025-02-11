package com.ceciliasuarez.project.repository;

import com.ceciliasuarez.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {

    boolean existsBySite(String site);

    boolean existsByRepository(String repository);
}
