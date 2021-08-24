
package dao;

import modules.Blackout;
import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class Sql2oBlackoutDaoTest {
    private static Connection conn;
    private static Sql2oBlackoutDao blackoutDao;

    @BeforeEach
    void setUp() {
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/lights_test",  "sirkadima", "kadima123");
        blackoutDao = new Sql2oBlackoutDao(sql2o);
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
        blackoutDao.getAll();
        Assertions.assertEquals(blackoutDao.getAll().get(0).isLights(), true);
    }

    @Test
    void findById() {
        Blackout blackout = new Blackout(true);
        blackoutDao.add(blackout);
        blackoutDao.getAll();
        Assertions.assertEquals(blackoutDao.getAll().get(0).getId(), 1);
    }

    @Test
    void getAll() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void clearAll() {
    }
}