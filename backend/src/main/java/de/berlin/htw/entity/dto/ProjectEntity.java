package de.berlin.htw.entity.dto;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import de.berlin.htw.lib.dto.ProjectJson;
import de.berlin.htw.lib.model.ProjectModel;

@NamedQuery(name = "ProjectEntity.deleteById", query = "delete from ProjectEntity project where project.id = :id")

@Entity
@Table(name = "PROJECT")
public class ProjectEntity extends AbstractEntity implements ProjectModel {

    @Column(name = "TITLE", nullable = false, length = 36)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false, length = 36)
    private String description;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "ID", nullable = false, length = 36)
    private UUID id;

    @ManyToMany(mappedBy = "projects")
    private Set<UserEntity> users;

    public ProjectEntity(ProjectJson json) {

        if (json.getId() != null) {
            this.id = UUID.fromString(json.getId());

        }
        this.title = json.getTitle();
        this.description = json.getDescription();
    }

    public ProjectEntity() {

    }

    @Override
    public String getId() {
        return id.toString();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    @Override
    public ArrayList<String> getUsers(){
        ArrayList<String> userList = new ArrayList<>();
        for (UserEntity userEntity : users) {
            userList.add(userEntity.getId());
        }
        return userList;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }


}
