package de.berlin.htw.entity.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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

@ManyToMany
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

    public Set<ProjectEntity> getProjects(){
        return projects;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProjects(Set<ProjectEntity> projects){
        this.projects = projects;
    }

    public void addTag(ProjectEntity project) {
        projects.add(project);
        project.getUsers().add(this);
    }

    
}
