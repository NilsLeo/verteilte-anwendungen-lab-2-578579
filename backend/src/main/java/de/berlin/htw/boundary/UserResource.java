package de.berlin.htw.boundary;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.berlin.htw.control.ProjectController;
import de.berlin.htw.control.UserController;
import de.berlin.htw.entity.dao.ProjectRepository;
import de.berlin.htw.entity.dto.ProjectEntity;
import de.berlin.htw.lib.UserEndpoint;
import de.berlin.htw.lib.dto.UserJson;
import de.berlin.htw.lib.model.UserModel;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@Path(UserEndpoint.CONTEXT + "/" + UserEndpoint.VERSION + "/" + UserEndpoint.SERVICE)
public class UserResource implements UserEndpoint {

    @Context
    UriInfo uri;

    @Inject
    UserController controller;
    @Inject
    ProjectRepository projectRepository;

    @Override
    public List<UserJson> getUsers() {
        final List<UserJson> users = new ArrayList<>();
        controller.getUsers().forEach(user -> users.add(new UserJson(user)));
        return users;
    }

    @Override
    public Response createUser(final UserJson user) {
        final String userId = controller.createUser(user);
        final URI location = uri.getAbsolutePathBuilder().path(userId).build();
        return Response.created(location).build();
    }

    @Override
    public UserJson getUser(final String userId) {
        try {
            final UserModel user = controller.getUser(userId);
            return new UserJson(user);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid user id", e);
        }
    }

    @Override
    public UserJson updateUser(final String userId, final UserJson user) {
        if (user.getId() != null) {
            throw new BadRequestException("User ID should not be set in payload");
        } else {
            user.setId(userId);
        }
        final UserModel updatedUser = controller.updateUser(user);
        return new UserJson(updatedUser);
    }

    @Override
    public UserJson updateUser(String userId, String projectId, UserJson user) {
        if (user.getId() != null) {
            throw new BadRequestException("User ID should not be set in payload");
        } else {
            ArrayList<ProjectEntity> projects= new ArrayList<>();
            projects.add(projectRepository.get(projectId));
            user.setProjects(projects);
        }
        final UserModel updatedUser = controller.updateUser(user);
        return new UserJson(updatedUser);
    }

    @Override
    public void deleteUser(final String userId) {
        if (!controller.deleteUser(userId)) {
            throw new NotFoundException();
        }
    }

}
