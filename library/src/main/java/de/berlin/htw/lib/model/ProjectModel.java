package de.berlin.htw.lib.model;

import java.util.ArrayList;

public interface ProjectModel {

    String getId();

    String getTitle();

    String getDescription();
    public ArrayList<String> getUsers();

}
