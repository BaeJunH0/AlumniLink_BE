package com.sparksInTheStep.webBoard.project.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.project.application.ProjectService;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectCommand;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectInfo;
import com.sparksInTheStep.webBoard.project.presentation.dto.ProjectRequest;
import com.sparksInTheStep.webBoard.project.presentation.dto.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> readAllProject(
            @PageableDefault Pageable pageable
    ){
        Page<ProjectInfo> projects = projectService.getAllProjects(pageable);
        return new ResponseEntity<>(projects.map(ProjectResponse::of), HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> readOneProject(
            @PathVariable Long projectId
    ){
        ProjectInfo project = projectService.getProject(projectId);
        return new ResponseEntity<>(ProjectResponse.of(project), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestBody ProjectRequest projectRequest,
            @AuthorizedUser MemberInfo.Default memberInfo
    ){
        projectService.makeProject(ProjectCommand.from(projectRequest, memberInfo.nickname()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<?> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectRequest projectRequest,
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        projectService.updateProject(
                projectId, ProjectCommand.from(projectRequest, memberInfo.nickname())
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(
            @PathVariable Long projectId,
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        projectService.deleteProject(projectId, memberInfo.nickname());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
