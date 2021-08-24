package dao;

import modules.Blackout;
import modules.User;

import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class Sql2oUserDao  implements IUser{
    private final Sql2o sql2o;

    public Sql2oUserDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (fname,lname,email,location,password) VALUES (:fname, :lname, :email,:location,:password)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addUserBlackout(User user, Blackout blackout) {
        String sql = "INSERT INTO blackouts_users (user_id, blackout_id) VALUES (:user_id, :blackout_id);";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("user_id", user.getId())
                    .addParameter("blackout_id", blackout.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }


    @Override
    public List<User> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public User findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }
    }

    @Override
    public List<Blackout> getUserBlackouts(int user_id) {
        ArrayList<Blackout> blackouts = new ArrayList<>();

        String joinQuery = "SELECT blackout_id FROM blackouts_users WHERE user_id = :user_id;";

        try (Connection con = sql2o.open()) {
            List<Integer> allBlackoutIds = con.createQuery(joinQuery)
                    .addParameter("user_id", user_id)
                    .executeAndFetch(Integer.class);
            for (Integer blackout_id : allBlackoutIds){
                String usersQuery = "SELECT * FROM blackouts WHERE id = :blackout_id";
                blackouts.add(
                        con.createQuery(usersQuery)
                                .addParameter("blackout_id", blackout_id)
                                .executeAndFetchFirst(Blackout.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return blackouts;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from users WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from users";
        String resetSql = "ALTER SEQUENCE users_id_seq RESTART WITH 1;";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
            con.createQuery(resetSql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}