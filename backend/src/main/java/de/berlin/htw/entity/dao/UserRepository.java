package de.berlin.htw.entity.dao;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import de.berlin.htw.entity.dto.UserEntity;

/**
 * @author Alexander Stanik [stanik@htw-berlin.de]
 */
@ApplicationScoped
@Transactional(TxType.MANDATORY)
public class UserRepository {

    @PersistenceContext
    EntityManager entityManager;
    
    @Transactional(TxType.NEVER)
    public List<UserEntity> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> rootEntry = cq.from(UserEntity.class);
        CriteriaQuery<UserEntity> all = cq.select(rootEntry);
        TypedQuery<UserEntity> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Transactional(TxType.SUPPORTS)
    public UserEntity get(final String userId) {
        final UUID id = UUID.fromString(userId);
        return entityManager.find(UserEntity.class, id);
    }

    public String add(@Valid final UserEntity user) {
        entityManager.persist(user);
        return user.getId();
    }

    public UserEntity set(@Valid final UserEntity user) {
        return entityManager.merge(user);
    }

    public boolean remove(final String userId) {
        final UUID id = UUID.fromString(userId);
        return entityManager.createNamedQuery("UserEntity.deleteById")
            .setParameter("id", id)
            .executeUpdate() > 0;
    }

    @Transactional(TxType.SUPPORTS)
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
