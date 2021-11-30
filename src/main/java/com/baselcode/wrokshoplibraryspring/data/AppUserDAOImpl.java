package com.baselcode.wrokshoplibraryspring.data;

import com.baselcode.wrokshoplibraryspring.data.interfaces.AppUserDAO;
import com.baselcode.wrokshoplibraryspring.models.AppUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.stream.Collectors;


@Repository
public class AppUserDAOImpl implements AppUserDAO {

    private final EntityManager _entityManager;

    public AppUserDAOImpl(EntityManager entityManager) {
        _entityManager = entityManager;
    }

    @Override
    public AppUser findById(int id) {
        if(id <= 0) throw new IllegalArgumentException("id must be greater than 0");
        return _entityManager.find(AppUser.class,id);
    }

    @Override
    public Collection<AppUser> findAll() {
        return _entityManager.createQuery("SELECT au FROM AppUser au",AppUser.class)
                .getResultList();
    }

    @Override
    public AppUser create(AppUser appUser) {
        _entityManager.persist(appUser);
        return appUser;
    }

    @Override
    public AppUser update(AppUser appUser) {
        return  _entityManager.merge(appUser);
    }

    @Override
    public void delete(int id) {
        _entityManager.remove(findById(id));
    }
}
