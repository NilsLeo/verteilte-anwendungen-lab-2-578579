package de.berlin.htw.boundary;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.berlin.htw.control.ProjectController;
import de.berlin.htw.lib.ProjectEndpoint;
import de.berlin.htw.lib.dto.ProjectJson;
import de.berlin.htw.lib.model.ProjectModel;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/" + ProjectEndpoint.SERVICE)
public class ProjectResource implements ProjectEndpoint{
    
    @Context
    UriInfo uri;

    @Inject
    ProjectController controller;

    @Override
    public Response createProject(ProjectJson project) {
        final String projectId = controller.createProject(project);
        final URI location = uri.getAbsolutePathBuilder().path(projectId).build();
        return Response.created(location).build();
    }

    @Override
    public List<ProjectJson> getProjects() {
        final List<ProjectJson> projects = new ArrayList<>();
        controller.getProjects().forEach(project -> projects.add(new ProjectJson(project)));
        return projects;
    }

    @Override
    public ProjectJson getProject(String projectId) {
                try {
            final ProjectModel project = controller.getProject(projectId);
            return new ProjectJson(project);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid project id", e);
        } 
    }

    @Override
    public ProjectJson updateProject(String projectId, @Valid ProjectJson project) {
        if (project.getId() != null) {
            throw new BadRequestException("Project ID should not be set in payload");
        } else {
            project.setId(projectId);
        }
        final ProjectModel updatedProject = controller.updateProject(project);
        return new ProjectJson(updatedProject);
    }

    @Override
    public void deleteProject(String projectId) {
        if (!controller.deleteProject(projectId)) {
            throw new NotFoundException();
        }
        
    }
}
