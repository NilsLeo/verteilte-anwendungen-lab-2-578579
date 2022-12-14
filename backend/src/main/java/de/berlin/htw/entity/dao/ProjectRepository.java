package de.berlin.htw.entity.dao;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.Valid;

import de.berlin.htw.entity.dto.ProjectEntity;

@RequestScoped
public class ProjectRepository {

    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    public List<ProjectEntity> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProjectEntity> cq = cb.createQuery(ProjectEntity.class);
        Root<ProjectEntity> rootEntry = cq.from(ProjectEntity.class);
        CriteriaQuery<ProjectEntity> all = cq.select(rootEntry);
        TypedQuery<ProjectEntity> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Transactional
    public ProjectEntity get(final String projectId) {
        final UUID id = UUID.fromString(projectId);
        return entityManager.find(ProjectEntity.class, id);
    }

    public boolean remove(final String projectId) {
        final UUID id = UUID.fromString(projectId);
        return entityManager.createNamedQuery("ProjectEntity.deleteById")
            .setParameter("id", id)
            .executeUpdate() > 0;
    }



    @Transactional
    public String add(ProjectEntity entity){
        entityManager.persist(entity);
        return entity.getId();
    }

    @Transactional
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ProjectEntity set(@Valid final ProjectEntity project) {
        return entityManager.merge(project);
    }
}
