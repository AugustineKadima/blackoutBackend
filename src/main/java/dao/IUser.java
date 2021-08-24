
package dao;


import modules.User;

import java.util.List;

public interface IUser {

    //create
    void add(User user);

    //read
    List<User> getAll();
    User findById(int id);

    //delete
    void deleteById(int id);
    void clearAll();
}