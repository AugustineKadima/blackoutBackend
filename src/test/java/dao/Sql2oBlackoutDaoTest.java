
package dao;

import modules.Blackout;
import modules.User;
import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oBlackoutDaoTest {
    private static Connection conn;
    private static Sql2oBlackoutDao blackoutDao;
    private static Sql2oUserDao userDao;

    @BeforeEach
    void setUp() {
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/lights_test",  "sirkadima", "kadima123");
        blackoutDao = new Sql2oBlackoutDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }
    @AfterEach
    void tearDown() {
        System.out.println("Clearing database");
        blackoutDao.clearAll();
    }
    @AfterAll
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    void add() {
        Blackout blackout = new Blackout(true);
        blackoutDao.add(blackout);
        Assertions.assertEquals(blackoutDao.getAll().get(0).isLights(), true);
    }

    @Test
    void findById() {
        Blackout blackout = new Blackout(true);
        blackoutDao.add(blackout);
        blackoutDao.getAll();
        Assertions.assertEquals(blackout, blackoutDao.findById(blackout.getId()));
    }

    @Test
    void getAll() {

        Blackout blackout1 = new Blackout(true);
        Blackout blackout2 = new Blackout(true);
        Blackout blackout3 = new Blackout(true);
        blackoutDao.add(blackout1);
        blackoutDao.add(blackout2);
        blackoutDao.add(blackout3);
        Assertions.assertEquals(blackoutDao.getAll().size(), 3);
    }

    @Test
    void deleteById() {
        Blackout blackout = new Blackout(true);
        Blackout blackout1 = new Blackout(true);
        blackoutDao.add(blackout);
        blackoutDao.add(blackout1);
        blackoutDao.deleteById(blackout.getId());
        Assertions.assertEquals(1,  blackoutDao.getAll().size());
    }

    @Test
    void clearAll() {
        Blackout blackout = new Blackout(true);
        blackoutDao.add(blackout);
        blackoutDao.deleteById(blackout.getId());
        Assertions.assertEquals(0,  blackoutDao.getAll().size());
    }

    @Test
    public void addUserToBlackout(){
        Blackout blackout = new Blackout(false);
        blackoutDao.add(blackout);

        User user = new User("John", "Doe", "johndoe@gmail.com", "jon", "jnjnjb");
        User user1 = new User("Sam", "Doe", "samdoe@gmail.com", "Ron", "hghgg");
        userDao.add(user);
        userDao.add(user1);

        blackoutDao.addBlackoutUser(user1, blackout);
        blackoutDao.addBlackoutUser(user, blackout);
        User[] users= {user1, user};
        assertEquals(Arrays.asList(users), blackoutDao.getBlackoutUser(blackout.getId()));
        blackoutDao.clearAll();
        userDao.clearAll();
    }
}
