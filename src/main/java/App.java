import com.google.gson.Gson;
import dao.Sql2oBlackoutDao;
import dao.Sql2oUserDao;
import modules.Blackout;
import modules.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Connection conn;
        Sql2oBlackoutDao blackoutDao;
        Sql2oUserDao userDao;
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/lights",  "sirkadima", "kadima123");

        userDao = new Sql2oUserDao(sql2o);
        blackoutDao = new Sql2oBlackoutDao(sql2o);

        conn = sql2o.open();
        Gson gson = new Gson();

        //CREATE nEW(aDD uSER)
        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //GET ALL
        get("/users", "application/json", (req, res) -> {
            return gson.toJson(userDao.getAll());
        });

        //GET USER BY ID
        get("/users/:id", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("id"));
            User userToFind = userDao.findById(userId);
            return gson.toJson(userToFind);
        });

        //DELETE BY ID
        delete("users/:user_id", (req, res) -> {
            int user_id = Integer.parseInt(req.params("user_id"));
            User userToDelete = userDao.findById(user_id);
            userDao.deleteById(user_id);
            return gson.toJson(userToDelete);
        });

        //CREATE nEW(aDD DEPARTMENT)
        post("/blackouts/new", "application/json", (req, res) -> {
            Blackout blackout = gson.fromJson(req.body(), Blackout.class);
            blackoutDao.add(blackout);
            res.status(201);
            return gson.toJson(blackout);
        });

        //GET ALL
        get("/blackouts", "application/json", (req, res) -> {
            return gson.toJson(blackoutDao.getAll());
        });

        //GET BLACKOUT BY ID
        get("/blackouts/:id", "application/json", (req, res) -> {
            int blackoutId = Integer.parseInt(req.params("id"));
            Blackout blackoutToFind = blackoutDao.findById(blackoutId);
            return gson.toJson(blackoutToFind);
        });

        //DELETE BY ID
        delete("blackouts/:blackout_id", (req, res) -> {
            int blackout_id = Integer.parseInt(req.params("blackout_id"));
            Blackout blackoutToDelete = blackoutDao.findById(blackout_id);
            blackoutDao.deleteById(blackout_id);
            return gson.toJson(blackoutToDelete);
        });


    }
}
