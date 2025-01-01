package com.sparksInTheStep.webBoard.project.persistent;

import com.sparksInTheStep.webBoard.project.doamin.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAll(Pageable pageable);

    Boolean existsByName(String name);
}
