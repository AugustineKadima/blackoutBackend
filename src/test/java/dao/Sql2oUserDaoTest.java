package dao;


import modules.Blackout;
import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import modules.User;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oUserDaoTest {
    private static Connection conn;
    private static Sql2oUserDao userDao;

    @BeforeEach
    void setUp() throws Exception {

        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/lights_test",  "sirkadima", "kadima123");
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Clearing database");
        userDao.clearAll();
    }
    @AfterAll
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("Connection closed");
    }
    @Test
    void add() {
        User user = new User("Victoria", "Okumu", "victoriaokumu@gmail.com", "Westlands", "123");
        userDao.add(user);
        Assertions.assertEquals(userDao.getAll().get(0).getFname(), "Victoria");
    }

    @Test
    void getAll() {
        User user = new User("Victoria", "Okumu", "victoriaokumu@gmail.com", "Westlands", "123");
        userDao.add(user);
        userDao.getAll();
        assertEquals(user, userDao.findById(user.getId()));
    }

    @Test
    void findById() {
        userDao.clearAll();
        User user = new User("Victoria", "Okumu", "victoriaokumu@gmail.com", "Westlands", "123");
        User user1 = new User("Augustine", "Samuel", "augustinesamuel@gmail.com", "Rhunda", "345");
        userDao.add(user);
        userDao.add(user1);
        assertEquals(userDao.getAll().size(), 2);
    }

    @Test
    void deleteById() {
        User user = new User("Victoria", "Okumu", "victoriaokumu@gmail.com", "Westlands", "123");
        User user1 = new User("Augustine", "Samuel", "augustinesamuel@gmail.com", "Rhunda", "345");
        userDao.add(user);
        userDao.add(user1);
        userDao.deleteById(user.getId());
        assertEquals(1,  userDao.getAll().size());
    }

    @Test
    void clearAll() {
        User user = new User("Victoria", "Okumu", "victoriaokumu@gmail.com", "Westlands", "123");
        userDao.add(user);
        userDao.deleteById(user.getId());
        assertEquals(0,  userDao.getAll().size());
    }
}