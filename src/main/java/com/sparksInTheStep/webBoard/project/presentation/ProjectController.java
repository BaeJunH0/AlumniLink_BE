package com.sparksInTheStep.webBoard.project.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.project.application.ProjectService;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectCommand;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectInfo;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectMemberRequestInfo;
import com.sparksInTheStep.webBoard.project.presentation.dto.Choice;
import com.sparksInTheStep.webBoard.project.presentation.dto.ProjectMemberRequestResponse;
import com.sparksInTheStep.webBoard.project.presentation.dto.ProjectRequest;
import com.sparksInTheStep.webBoard.project.presentation.dto.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController implements ProjectApiSpec{
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

    @GetMapping("/my")
    public ResponseEntity<?> readMyProject(
            @PageableDefault Pageable pageable,
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        Page<ProjectInfo> projects = projectService.getMyProjects(pageable, memberInfo.nickname());
        return new ResponseEntity<>(projects.map(ProjectResponse::of), HttpStatus.OK);
    }

    @DeleteMapping("/my/{projectId}")
    public ResponseEntity<?> withdrawProject(
            @PathVariable Long projectId,
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        projectService.withdrawProject(projectId, memberInfo.nickname());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<?> joinToProject(
            @PathVariable Long projectId,
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        projectService.joinProject(projectId, memberInfo.nickname());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/requests")
    public ResponseEntity<?> readMyProjectRequest(
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        List<ProjectMemberRequestInfo> requests =
                projectService.readProjectJoinRequest(memberInfo.nickname());

        return new ResponseEntity<>(
                requests.stream().map(ProjectMemberRequestResponse::of).toList(),
                HttpStatus.OK
        );
    }

    @PostMapping("/requests/{requestId}")
    public ResponseEntity<?> choiceRequest(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody Choice tf,
            @PathVariable Long requestId
    ) {
        projectService.choice(memberInfo.nickname(), requestId, tf.tf());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
