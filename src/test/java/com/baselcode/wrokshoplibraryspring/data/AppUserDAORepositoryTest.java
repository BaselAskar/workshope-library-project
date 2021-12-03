package com.baselcode.wrokshoplibraryspring.data;

import com.baselcode.wrokshoplibraryspring.data.interfaces.AppUserDAO;
import com.baselcode.wrokshoplibraryspring.models.AppUser;
import com.baselcode.wrokshoplibraryspring.models.Details;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class AppUserDAORepositoryTest {

    @Autowired
    private AppUserDAORepository testObject;

    @Autowired
    private TestEntityManager testEntityManager;



    private final String USER_NAME = "Basel Askar";
    private final String PASSWORD = "123456";
    private final LocalDate REG_DATE = LocalDate.parse("2020-09-14");
    private final Details DETAILS = new Details("basel@gmail.com","Basel",LocalDate.parse("1991-09-03"));

    public List<AppUser> users(){ return new ArrayList<>(
            Arrays.asList(
            new AppUser(USER_NAME, PASSWORD, REG_DATE, DETAILS),
                        new AppUser("Karmand Aziz","123456",LocalDate.parse("2020-10-02"),new Details("karmand@gmail.com","Karmand",LocalDate.parse("1994-05-03"))),
            new AppUser("Erik Svensson","2154235",LocalDate.parse("2019-04-18"),new Details("erik@gmail.com","Erik",LocalDate.parse("1980-07-03")))
            )
            );
    }


    private AppUser firstUser;

    @BeforeEach
    void setUp() {

       List<AppUser> persistedUsers = users().stream()
               .map(testEntityManager::persist)
               .collect(Collectors.toList());

       firstUser = persistedUsers.get(0);
    }

    @Test
    void findById() {
        int id = firstUser.getAppUserId();
        AppUser appUser = testObject.findById(id);

        assertNotNull(appUser);
        assertEquals(id,appUser.getAppUserId());
        assertEquals(USER_NAME,appUser.getUsername());
        assertEquals(PASSWORD,appUser.getPassword());
        assertEquals(REG_DATE,appUser.getRegDate());
        assertEquals(DETAILS,appUser.getUserDetails());
    }

    @Test
    void findAll() {
        List<AppUser> testUsers = (List<AppUser>) testObject.findAll();

        int expected = 3;

        assertEquals(expected,testUsers.size());
    }

    @Test
    void create() {
        AppUser newUser = new AppUser("Maria johansson","21533216",LocalDate.parse("2021-10-05"),
                new Details("maria@gmail.com","Maria",LocalDate.parse("1985-04-17")));

        int expected = 4;

        newUser = testObject.create(newUser);

        assertEquals(4,testObject.findAll().size());
        assertTrue(newUser.getAppUserId() > 0);
    }

    @Test
    void update() {
        int id = firstUser.getAppUserId();

        AppUser updatedUser = firstUser;
        updatedUser.setPassword("basel123basel");

        AppUser newUser = testObject.update(updatedUser);

        assertEquals(newUser,testObject.findById(id));
    }

    @Test
    void delete() {
        int id = firstUser.getAppUserId();
        testObject.delete(id);


        assertNull(testEntityManager.find(AppUser.class,id));

    }
}