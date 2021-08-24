package dao;

import modules.Blackout;
import modules.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oBlackoutDao implements IBlackout{
    private final Sql2o sql2o;

    public Sql2oBlackoutDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Blackout blackout) {
        String sql = "INSERT INTO blackouts (lights) VALUES (:lights)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(blackout)
                    .executeUpdate()
                    .getKey();
            blackout.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void addBlackoutUser(User user, Blackout blackout) {
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
    public Blackout findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM blackouts WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Blackout.class);
        }
    }

    @Override
    public List<User> getBlackoutUser(int blackout_id) {
        ArrayList<User> users = new ArrayList<>();

        String joinQuery = "SELECT user_id FROM blackouts_users WHERE blackout_id = :blackout_id;";

        try (Connection con = sql2o.open()) {
            List<Integer> allUserIds = con.createQuery(joinQuery)
                    .addParameter("blackout_id", blackout_id)
                    .executeAndFetch(Integer.class);
            for (Integer user_id : allUserIds){
                String usersQuery = "SELECT * FROM users WHERE id = :user_id";
                users.add(
                        con.createQuery(usersQuery)
                                .addParameter("user_id", user_id)
                                .executeAndFetchFirst(User.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return users;
    }

    @Override
    public List<Blackout> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM blackouts")
                    .executeAndFetch(Blackout.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from blackouts WHERE id = :id";
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
        String sql = "DELETE from blackouts";
        String resetSql = "ALTER SEQUENCE blackout_id_seq RESTART WITH 1;";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
            con.createQuery(resetSql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}

