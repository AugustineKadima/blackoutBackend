
package modules;


import java.util.Objects;

public class User {
    private int id;
    private String fname;
    private String lname;
    private String email;
    private String location;
    private String password;

    public User(String fname, String lname, String email, String location, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.location = location;
        this.password = password;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && fname.equals(user.fname) && lname.equals(user.lname) && email.equals(user.email) && location.equals(user.location) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fname, lname, email, location, password);
    }
}