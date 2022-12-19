package de.berlin.htw.lib.dto;
import de.berlin.htw.lib.model.ProjectModel;

import java.util.ArrayList;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
public class ProjectJson implements ProjectModel{

    private String id;
    private String title;
    private String description;
    private ArrayList<String> users;

    @Override
    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ProjectJson(){

    }
    public ProjectJson(ProjectModel project){
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.users=project.getUsers();
    }

    @Override
    public String getId() {
        return id;
    }
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getDescription() {
        return description;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}
