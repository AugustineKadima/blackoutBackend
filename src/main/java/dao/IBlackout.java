
package dao;

import modules.Blackout;
import modules.User;

import java.util.List;

public interface IBlackout {

    //create
    void add(Blackout blackout);


    //read
    List<Blackout> getAll();
    Blackout findById(int id);

    //update


    //delete
    void deleteById(int id);
    void clearAll();
}