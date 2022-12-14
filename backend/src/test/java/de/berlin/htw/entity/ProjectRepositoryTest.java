package de.berlin.htw.entity;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import de.berlin.htw.AbstractTest;
import de.berlin.htw.control.ProjectController;
import de.berlin.htw.entity.dao.ProjectRepository;
import de.berlin.htw.entity.dto.ProjectEntity;

@QuarkusTest
class ProjectRepositoryTest extends AbstractTest {

    @Inject
    ProjectRepository repository;

    @Inject
    ProjectController controller;

    @Inject
    UserTransaction transaction;

    @Inject
    ProjectEntity entity;

    @AfterEach
    void cleanUp() throws Exception {
        if (transaction.getStatus() != Status.STATUS_NO_TRANSACTION) {
            transaction.rollback();
        }
    }

    @Test
    void testAdd() throws Exception {

        String description = "dessscriptiopn";
        String title = "Tiiitle";
        String id = "38400000-8cf0-11bd-b23e-10b96e4ef00d";
        entity.setDescription(description);
        entity.setTitle(title);
        entity.setId(id);
        final String projectId = repository.add(entity);
        assertEquals(36, projectId.length());
        assertEquals(title, repository.get(projectId).getTitle());
        assertEquals(description, repository.get(projectId).getDescription());
    }
    @Test
    void testDeleteProject(){

    }
    // @Test
    // void testUpdateProject(){

    //     String id = "38400000-8cf0-11bd-b23e-10b96e4ef00d";
    //     repository.set(entity);

    // }
    @Test
    void testGetProjects(){

    }
    @Test
    void testGetProject(){

    }


}