package modules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void instanceSuccessfullyCreated(){
        User user = new User(
                1,
                "Mark",
                "Muigai",
                "markmuigai@gmail.com",
                "Kimbo",
                "******"
        );
        Assertions.assertEquals(true, user instanceof User);
    }

    @Test
    public void variablesCanBeAssigned(){
        User user = new User(
                1,
                "Mark",
                "Muigai",
                "markmuigai@gmail.com",
                "Kimbo",
                "******"
        );
        user.setFname("Victoria");
        user.setLname("Okumu");
        user.setEmail("victoriaokumu@gmail.com");
        user.setLocation("Westlands");
        user.setPassword("123");
        user.setId(4);
        Assertions.assertEquals("Victoria", user.getFname());
        Assertions.assertEquals("Okumu", user.getLname());
        Assertions.assertEquals("victoriaokumu@gmail.com", user.getEmail());
        Assertions.assertEquals("Westlands", user.getLocation());
        Assertions.assertEquals("123", user.getPassword());
        Assertions.assertEquals(4, user.getId());
    }
}
