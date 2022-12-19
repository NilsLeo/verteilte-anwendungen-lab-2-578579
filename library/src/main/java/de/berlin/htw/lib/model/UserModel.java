package de.berlin.htw.lib.model;

import java.util.ArrayList;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
public interface UserModel {

    String getId();

    String getName();

    String getEmail();

    ArrayList<String> getProjects();

}
