package com.baselcode.wrokshoplibraryspring.data;

import com.baselcode.wrokshoplibraryspring.data.interfaces.AppUserDAO;
import com.baselcode.wrokshoplibraryspring.models.AppUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;


@Repository
public class AppUserDAORepository implements AppUserDAO {

    private final EntityManager _entityManager;

    public AppUserDAORepository(EntityManager entityManager) {
        _entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findById(int id) {
        if(id <= 0) throw new IllegalArgumentException("id must be greater than 0");
        return _entityManager.find(AppUser.class,id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AppUser> findAll() {
        return _entityManager.createQuery("SELECT au FROM AppUser au",AppUser.class)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser create(AppUser appUser) {
        if(appUser == null) throw new IllegalArgumentException("appUser was null!!");
        _entityManager.persist(appUser);
        return appUser;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser update(AppUser appUser) {
        if(appUser == null) throw new IllegalArgumentException("user was null!!");
        if(findById(appUser.getAppUserId()) == null) throw new RuntimeException("user is not found");

        return  _entityManager.merge(appUser);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(int id) {

        if(findById(id) == null) throw new RuntimeException("user was not found");

        _entityManager.remove(findById(id));
    }


}
