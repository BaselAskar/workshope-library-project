package com.baselcode.wrokshoplibraryspring.data;

import com.baselcode.wrokshoplibraryspring.data.interfaces.AuthorDAO;
import com.baselcode.wrokshoplibraryspring.models.Author;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
public class AuthorDAORepository implements AuthorDAO {

    private final EntityManager _entityManager;

    public AuthorDAORepository(EntityManager _entityManager) {
        this._entityManager = _entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Author findById(int id) {
        if(id <= 0) throw new IllegalArgumentException("id must be greater than 0");

        return _entityManager.find(Author.class,id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Author> findAll() {
        return _entityManager.createQuery("SELECT au FROM Author au",Author.class)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Author create(Author author) {
        _entityManager.persist(author);
        return author;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Author update(Author author) {
        return _entityManager.merge(author);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(int id) {
        Author author = findById(id);
        if(author == null) throw new IllegalArgumentException("author is not found!!");
        _entityManager.remove(author);
    }
}
