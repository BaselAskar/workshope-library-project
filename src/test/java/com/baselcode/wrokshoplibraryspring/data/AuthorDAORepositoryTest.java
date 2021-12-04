package com.baselcode.wrokshoplibraryspring.data;

import com.baselcode.wrokshoplibraryspring.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class AuthorDAORepositoryTest {

    public final String FIRST_NAME = "Jons";
    public final String LAST_NAME = "Johansson";
    @Autowired
    private AuthorDAORepository testObject;

    @Autowired
    private TestEntityManager testEntityManager;


    public List<Author> authors(){
        return new ArrayList<>(
                Arrays.asList(
                        new Author(FIRST_NAME, LAST_NAME),
                        new Author("Rekard","Dahelberg"),
                        new Author("Natasha","lopez")
                )
        );
    }

    private Author firstAuthor;

    private List<Author> persistedAuthors;

    @BeforeEach
    void setUp() {
        persistedAuthors = authors().stream()
                .map(testEntityManager::persist)
                .collect(Collectors.toList());

        firstAuthor = persistedAuthors.get(0);

    }

    @Test
    void findById() {
        int id = firstAuthor.getAuthorId();

        Author author = testObject.findById(id);

        assertNotNull(author);
        assertEquals(FIRST_NAME,author.getFirstName());
        assertEquals(LAST_NAME,author.getLastName());
    }

    @Test
    void findAll() {
        Collection<Author> testAuthors = testObject.findAll();

        int expected = 3;

        assertEquals(expected,testAuthors.size());
    }

    @Test
    void create() {
        Author newAuthor = new Author("Jinica","Pertersson");

        newAuthor = testObject.create(newAuthor);

        int id = newAuthor.getAuthorId();
        int expected = 4;

        assertNotNull(testObject.findById(id));
        assertEquals(expected,testObject.findAll().size());
    }

    @Test
    void update() {
        String newLastName = "Alfred";
        firstAuthor.setLastName(newLastName);

        int id  = firstAuthor.getAuthorId();

        Author updatedAuthor = testObject.update(firstAuthor);

        assertEquals(newLastName,testObject.findById(id).getLastName());
    }

    @Test
    void delete() {
        int id = firstAuthor.getAuthorId();

        testObject.delete(id);

        assertNull(testObject.findById(id));
    }
}