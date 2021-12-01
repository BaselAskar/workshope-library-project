package com.baselcode.wrokshoplibraryspring.data;

import com.baselcode.wrokshoplibraryspring.data.interfaces.DetailsDAO;
import com.baselcode.wrokshoplibraryspring.models.Details;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class DetailsDAORepository implements DetailsDAO {

    private final EntityManager _entityManager;

    public DetailsDAORepository(EntityManager entityManager) {
        _entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Details findById(int id) {
        if(id <= 0) throw new IllegalArgumentException("id must be greater than 0");

        return _entityManager.find(Details.class,id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Details> findAll() {
        return _entityManager.createQuery("SELECT d FROM Details d",Details.class)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Details create(Details details) {
        _entityManager.persist(details);
        return details;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Details update(Details details) {
        if(details == null) throw new IllegalArgumentException("details was null!!");
        return _entityManager.merge(details);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(int id) {
        _entityManager.remove(findById(id));
    }
}
