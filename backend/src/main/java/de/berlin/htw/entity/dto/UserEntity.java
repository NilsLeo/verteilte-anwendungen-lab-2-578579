package de.berlin.htw.entity.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import de.berlin.htw.entity.dao.ProjectRepository;
import de.berlin.htw.lib.model.ProjectModel;
import de.berlin.htw.lib.model.UserModel;
import liquibase.hub.model.Project;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@NamedQuery(name = "UserEntity.deleteById", query = "delete from UserEntity user where user.id = :id")
@Entity
@Table(name = "USER")
public class UserEntity extends AbstractEntity implements UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "ID", nullable = false, length = 36)
    private UUID id;

@ManyToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
@JoinTable(
name = "PROJECT_USER_MAPPING",
joinColumns = @JoinColumn(name = "USER_ID"),
inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
private Set<ProjectEntity> projects;

    @Column(name = "NAME")
    private String name;

    @Email
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Override
    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public ArrayList<String> getProjects(){
        ArrayList<String> projectList = new ArrayList<>();
        for (ProjectEntity projectEntity : projects) {
            projectList.add(projectEntity.getId());
        }
        return projectList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProjects(Set<ProjectEntity> projects){
        this.projects = projects;
    }



}
