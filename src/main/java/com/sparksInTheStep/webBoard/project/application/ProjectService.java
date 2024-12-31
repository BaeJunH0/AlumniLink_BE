package com.sparksInTheStep.webBoard.project.application;

import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.ProjectErrorCode;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectCommand;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectInfo;
import com.sparksInTheStep.webBoard.project.doamin.Project;
import com.sparksInTheStep.webBoard.project.persistent.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public Page<ProjectInfo> getAllProjects(Pageable pageable){
        return projectRepository.findAll(pageable).map(ProjectInfo::of);
    }
    @Transactional(readOnly = true)
    public ProjectInfo getProject(Long id){
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        return ProjectInfo.of(project);
    }
    @Transactional
    public void makeProject(ProjectCommand projectCommand){
        if(projectRepository.existsByName(projectCommand.name())) {
            throw CustomException.of(ProjectErrorCode.ALREADY_EXIST_NAME);
        }
        Project project = Project.of(projectCommand);
        projectRepository.save(project);
    }
    @Transactional
    public void updateProject(Long id, ProjectCommand projectCommand){
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        if(projectRepository.existsByName(projectCommand.name())) {
            throw CustomException.of(ProjectErrorCode.ALREADY_EXIST_NAME);
        }
        if(!project.getName().equals(projectCommand.nickname())) {
            throw CustomException.of(ProjectErrorCode.NOT_TEAM_LEADER);
        }

        project.update(projectCommand.name(), projectCommand.info(), projectCommand.gitLink());
    }

    @Transactional
    public void deleteProject(Long id, String nickname){
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        if(!project.getName().equals(nickname)) {
            throw CustomException.of(ProjectErrorCode.NOT_TEAM_LEADER);
        }

        projectRepository.delete(project);
    }
}
