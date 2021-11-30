package com.baselcode.wrokshoplibraryspring.data;

import com.baselcode.wrokshoplibraryspring.data.interfaces.DetailsDAO;
import com.baselcode.wrokshoplibraryspring.models.Details;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public class DetailsDAOImpl implements DetailsDAO {

    private final EntityManager _entityManager;

    public DetailsDAOImpl(EntityManager _entityManager) {
        this._entityManager = _entityManager;
    }

    @Override
    public Details findById(int id) {
        return _entityManager.find(Details.class,id);
    }

    @Override
    public Collection<Details> findAll() {
        return _entityManager.createQuery("SELECT d FROM Details d",Details.class)
                .getResultList();
    }

    @Override
    public Details create(Details details) {
        _entityManager.persist(details);
        return details;
    }

    @Override
    public Details update(Details details) {
        return _entityManager.merge(details);
    }

    @Override
    public void delete(int id) {
        _entityManager.remove(findById(id));
    }
}
