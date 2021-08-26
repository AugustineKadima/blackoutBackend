import com.google.gson.Gson;
import dao.Sql2oBlackoutDao;
import dao.Sql2oUserDao;
import modules.Blackout;
import modules.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());

//                Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-18-235-4-83.compute-1.amazonaws.com:5432/d8spnuo1k23hg0",
//                        "lpjsgfsgabkeqy", "1e71807b185403536ef918f5b1913e5abf37aa8755935eeedcfb5771d2a9cdff");

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

        //USERS_BLACKOUTS
        post("/blackouts/:blackout_id/user/:user_id/new", "application/json", (req, res) -> {
            int blackout_id = Integer.parseInt(req.params("blackout_id"));
            int user_id = Integer.parseInt(req.params("user_id"));
            Blackout blackout = blackoutDao.findById(blackout_id);
            User user = userDao.findById(user_id);

            if (blackout!= null && user != null){
                userDao.addUserBlackout(user, blackout);
                blackoutDao.addBlackoutUser(user, blackout);
                res.status(201);
                return gson.toJson(String.format("New blackout alert has been raised by %s in  %s",user.getFname(), user.getLocation()));
            }
            else {
                throw new Exception();
            }
        });

        //ALL USERS PER BLACKOUT
        get("/blackouts/:blackout_id/users", "application/json", (req, res) -> {
            int blackout_id = Integer.parseInt(req.params("blackout_id"));
            Blackout blackoutToFind = blackoutDao.findById(blackout_id);
            if (blackoutToFind == null) {
                throw new Exception();
            }
//            else if (blackoutDao.getBlackoutUser(blackout_id).size() == 0) {
//                return "{\"message\":\"There are no employees in this department.\"}";
//            }
//
//            else {
//                return gson.toJson(blackoutDao.getBlackoutUser(blackout_id));
//            }

            List<User> allUsers = userDao.getAllUsersByBlackout(blackout_id);
            return gson.toJson(allUsers);
        });

        get("/user/:user_id/blackouts", "application/json", (req, res) -> {
            int user_id = Integer.parseInt(req.params("user_id"));
            User userToFind = userDao.findById(user_id);
            if (userToFind == null){
                return  "{\"message\":\"Empty\"}";
            }
            else if (userDao.getUserBlackouts(user_id).size() == 0){
                return "{\"message\":\"This employee is currently not associated with any department\"}";
            }
            else {
                return gson.toJson(userDao.getUserBlackouts(user_id));
            }
        });

    }
}
