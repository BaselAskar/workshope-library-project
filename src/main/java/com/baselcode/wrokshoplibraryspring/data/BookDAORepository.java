package com.baselcode.wrokshoplibraryspring.data;

import com.baselcode.wrokshoplibraryspring.data.interfaces.DAOGenericeCRUD;
import com.baselcode.wrokshoplibraryspring.models.Book;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

public class BookDAORepository implements DAOGenericeCRUD<Book> {

    private final EntityManager _entityManager;

    public BookDAORepository(EntityManager entityManager) {
        _entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(int id) {
        if(id <= 0) throw new IllegalArgumentException("id must be greater than 0");
        return _entityManager.find(Book.class,id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Book> findAll() {
        return _entityManager.createQuery("SELECT b FROM Book b",Book.class)
                .getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Book create(Book book) {
        if(book == null) throw new IllegalArgumentException("book was null!!");

        _entityManager.persist(book);
        return book;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Book update(Book book) {

        return _entityManager.merge(book);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(int id) {
        _entityManager.remove(findById(id));
    }
}
