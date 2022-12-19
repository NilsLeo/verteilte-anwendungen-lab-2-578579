package de.berlin.htw.entity;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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
    static final String DESCRIPTION2 = "Description2";

    @Inject
    UserRepository userRepository;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    UserTransaction transaction;

    @AfterEach
    void cleanUp() throws Exception {
        if (transaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
            transaction.rollback();
        }
    }
    
    @Test
    void testAddAndGet() throws Exception {

        final ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDescription(DESCRIPTION);
        projectEntity.setTitle(TITLE);
        
        transaction.begin();
        
        final String projectId = projectRepository.add(projectEntity);
        assertNotNull(projectId);
        assertEquals(36, projectId.length());
        
        transaction.commit();
        projectRepository.getEntityManager().clear();
        
        assertEquals(TITLE, projectRepository.get(projectId).getTitle());
        assertEquals(DESCRIPTION, projectRepository.get(projectId).getDescription());
    }

    @Test
    void testDelete() throws Exception {

        final ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDescription(DESCRIPTION);
        projectEntity.setTitle(TITLE);
        
        transaction.begin();
        final String projectId = projectRepository.add(projectEntity);
        transaction.commit();

        transaction.begin();
        projectRepository.remove(projectId);
        transaction.commit();

        projectRepository.getEntityManager().clear();
        assertThrows(NullPointerException.class,
        ()->{
        projectRepository.get(projectId);
        });
    }

    @Test
    void testUpdate() throws Exception {

        final ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDescription(DESCRIPTION);
        projectEntity.setTitle(TITLE);
        
        transaction.begin();
        final String projectId = projectRepository.add(projectEntity);
        projectEntity.setId(projectId);
        transaction.commit();

        transaction.begin();
        projectEntity.setDescription(DESCRIPTION2);
        projectRepository.set(projectEntity);
        transaction.commit();

        projectRepository.getEntityManager().clear();

        assertEquals(DESCRIPTION2, projectRepository.get(projectId).getDescription());

    }


    @Test
    void testGetProjects()throws Exception{

        final ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDescription(DESCRIPTION);
        projectEntity.setTitle(TITLE);
        
        transaction.begin();
        
        final String projectId = projectRepository.add(projectEntity);
        assertNotNull(projectId);
        assertEquals(36, projectId.length());
        
        transaction.commit();
        projectRepository.getEntityManager().clear();
        assertEquals(36, projectRepository.getAll().get(0).getId().length());
    }
}
