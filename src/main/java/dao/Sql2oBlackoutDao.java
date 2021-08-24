package dao;

import modules.Blackout;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

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
    public Blackout findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM blackouts WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Blackout.class);
        }
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
        String sql = "DELETE from blacouts";
        String resetSql = "ALTER SEQUENCE blackout_id_seq RESTART WITH 1;";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
            con.createQuery(resetSql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}

