
package dao;


import modules.Blackout;
import modules.User;

import java.util.List;

public interface IUser {

    //create
    void add(User user);
    void addUserBlackout(User user, Blackout blackout);

    //read
    List<User> getAll();
    User findById(int id);
    List<Blackout> getUserBlackouts(int user_id);
    List<User> getAllUsersByBlackout(int blackout_id);
    //delete
    void deleteById(int id);
    void clearAll();
}