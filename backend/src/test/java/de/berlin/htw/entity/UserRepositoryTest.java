package de.berlin.htw.entity;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
class UserRepositoryTest extends AbstractTest {
    
    static final String NAME = "Max Mustermann";
    static final String EMAIL = "max.mustermann@example.org";
    static final String TITLE = "Title";
    static final String DESCRIPTION = "Description";

    @Inject
    UserRepository repository;
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
    void testTransactionRequired() {
        assertThrows(
            TransactionalException.class,
            () -> repository.add(new UserEntity()));
    }

    @Test
    void testAddAndGet() throws Exception {
        final UserEntity userEntity = new UserEntity();
        userEntity.setName(NAME);
        userEntity.setEmail(EMAIL);

        final ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setDescription(DESCRIPTION);
        projectEntity.setTitle(TITLE);
        Set<ProjectEntity> projects = new HashSet<>();
        projects.add(projectEntity);
        userEntity.setProjects(projects);
        transaction.begin();
        final String projectId = projectRepository.add(projectEntity);
        final String userId = repository.add(userEntity);
        assertNotNull(userId);
        assertEquals(36, userId.length());
        transaction.commit();
        repository.getEntityManager().clear();

        assertEquals(NAME, repository.get(userId).getName());
        assertEquals(EMAIL, repository.get(userId).getEmail());
    }

    @Test
    void testValidationOnAdd() throws Exception {
        final UserEntity entity = new UserEntity();
        entity.setName(EMAIL);
        entity.setEmail(NAME);

        transaction.begin();
        assertThrows(
            ConstraintViolationException.class,
            () -> repository.add(entity));
        transaction.rollback();
    }

}