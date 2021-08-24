package dao;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    }

    @Test
    void getAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void clearAll() {
    }
}