package de.berlin.htw.entity;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.HashSet;

import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.TransactionalException;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import de.berlin.htw.AbstractTest;
import de.berlin.htw.entity.dao.ProjectRepository;
import de.berlin.htw.entity.dao.UserRepository;
import de.berlin.htw.entity.dto.ProjectEntity;
import de.berlin.htw.entity.dto.UserEntity;
@QuarkusTest
public class ProjectRepositoryTest {
    static final String NAME = "Max Mustermann";
    static final String EMAIL = "max.mustermann@example.org";
    static final String TITLE = "Title";
    static final String DESCRIPTION = "Description";

    @Inject
    UserRepository userRepository;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    UserTransaction transaction;

    // @Test
    // void testAddAndGet() throws Exception {

    //     final ProjectEntity projectEntity = new ProjectEntity();
    //     projectEntity.setDescription(DESCRIPTION);
    //     projectEntity.setTitle(TITLE);
        
    //     transaction.begin();
        
    //     final String projectId = projectRepository.add(projectEntity);
    //     assertNotNull(projectId);
    //     assertEquals(36, projectId.length());
        
    //     transaction.commit();
    //     projectRepository.getEntityManager().clear();
        
    //     assertEquals(TITLE, projectRepository.get(projectId).getTitle());
    //     assertEquals(DESCRIPTION, projectRepository.get(projectId).getDescription());
    // }
    // @Test
    // void testDeleteProject() throws Exception {
    //     final ProjectEntity projectEntity = new ProjectEntity();
    //     projectEntity.setDescription(DESCRIPTION);
    //     projectEntity.setTitle(TITLE);
        
    //     transaction.begin();
        
    //     final String projectId = projectRepository.add(projectEntity);
    //     projectRepository.remove(projectId);
        
    //     transaction.commit();
    //     projectRepository.getEntityManager().clear();
    //     transaction.begin();
    //     assertNull(projectRepository.get(projectId));
    //     transaction.commit();
    //     projectRepository.getEntityManager().clear();
    // }


    // @Test
    // void testUpdateProject() throws Exception {
        
    //     final ProjectEntity projectEntity = new ProjectEntity();

    //     projectEntity.setDescription(DESCRIPTION);
    //     projectEntity.setTitle(TITLE);

    //     transaction.begin();
    //     final String projectId = projectRepository.add(projectEntity);
    //     projectEntity.setId(projectId);
    //     transaction.commit();
    //     repository.getEntityManager().clear();


    //     projectEntity.setDescription(DESCRIPTION2);
        
    //     transaction.begin();
    //     projectRepository.set(projectEntity);
    //     transaction.commit();
    //     repository.getEntityManager().clear();
        
    //     assertEquals(DESCRIPTION2, repository.get(projectId).getDescription());

    // }
    // @Test
    // void testGetProjects()throws Exception{

    //     final ProjectEntity projectEntity = new ProjectEntity();
    //     projectEntity.setDescription(DESCRIPTION);
    //     projectEntity.setTitle(TITLE);
        
    //     transaction.begin();
        
    //     final String projectId = projectRepository.add(projectEntity);
    //     assertNotNull(projectId);
    //     assertEquals(36, projectId.length());
        
    //     transaction.commit();
    //     projectRepository.getEntityManager().clear();
    //     assertEquals(36, projectRepository.getAll().get(0).getId().length());
    // }

    // @Test
    // void testAssociatedUsers()throws Exception{
    //     final UserEntity userEntity = new UserEntity();
    //     userEntity.setName(NAME);
    //     userEntity.setEmail(EMAIL);

    //     final ProjectEntity projectEntity = new ProjectEntity();
    //     projectEntity.setDescription(DESCRIPTION);
    //     projectEntity.setTitle(TITLE);

    //     Set<UserEntity> users = new HashSet<>();
    //     users.add(userEntity);
    //     projectEntity.setUsers(users);

    //     transaction.begin();
    //     final String userId = userRepository.add(userEntity);

    //     transaction.commit();


    // }
}
