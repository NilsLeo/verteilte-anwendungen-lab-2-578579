package de.berlin.htw.lib;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import de.berlin.htw.lib.dto.ProjectJson;

@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/" + ProjectEndpoint.SERVICE)
public interface ProjectEndpoint {
    
    final static String CONTEXT = "api";
    final static String VERSION = "v2";
    final static String SERVICE = "projects";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve information of all projects.")
    List<ProjectJson> getProjects();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new project.")
    @APIResponse(responseCode = "201", description = "Project created successfully",
        headers = @Header(name = "Location", description = "URL to retrive all orders"))
    @APIResponse(responseCode = "400", description = "Invalid request message")
    Response createProject(
        @Parameter(description = "Project information", required = true) @Valid ProjectJson project);

@GET
@Path("/{projectId}")
@Produces(MediaType.APPLICATION_JSON)
@Operation(summary = "Retrieve information of a project.")
@APIResponse(responseCode = "404", description = "The project does not exist")
ProjectJson getProject(
    @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId);


    @PATCH
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update information of a project.")
    @APIResponse(responseCode = "404", description = "The project does not exist")
    ProjectJson updateProject(
        @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId,
        @Parameter(description = "Project information", required = true) @Valid ProjectJson project);

@DELETE
@Path("/{projectId}")
@Operation(summary = "Delete an existing project.")
@APIResponse(responseCode = "204", description = "The project was deleted successfully")
@APIResponse(responseCode = "404", description = "The project does not exist")
void deleteProject(
    @Parameter(description = "ID of the project", required = true) @PathParam("projectId") String projectId);
}





