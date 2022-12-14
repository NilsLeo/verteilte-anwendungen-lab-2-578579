package de.berlin.htw.control;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import de.berlin.htw.entity.dao.ProjectRepository;
import de.berlin.htw.entity.dto.ProjectEntity;
import de.berlin.htw.lib.dto.ProjectJson;
import de.berlin.htw.lib.model.ProjectModel;
@RequestScoped
@Transactional
public class ProjectController{

    @Inject
    ProjectRepository repository;

    public String createProject(ProjectJson json){
        ProjectEntity entity = new ProjectEntity(json);
        return repository.add(entity);
    }
    public List<? extends ProjectModel> getProjects() {
        return repository.getAll();
    }

    public ProjectModel getProject(final String projectId) {
        final ProjectModel project = repository.get(projectId);
        if(project == null) {
            throw new NotFoundException("Project not exist");
        }
        return project;
    }

    @Transactional
    public ProjectModel updateProject(final ProjectModel project) {
        final ProjectEntity entity = repository.get(project.getId());
        if(project.getTitle() != null) {
            entity.setTitle(project.getTitle());
        }
        if(project.getDescription() != null) {
            entity.setDescription(project.getDescription());
        }
        return repository.set(entity);
    }

    @Transactional
    public boolean deleteProject(final String projectId) {
        return repository.remove(projectId);
    }

}
