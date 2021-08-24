package modules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlackoutTest {

    @Test
    public void BlackoutClassExists(){
        Blackout blackout = new Blackout(true);
        Assertions.assertEquals(true, blackout instanceof Blackout);
    }

    @Test
    public void idAndLightsCanBeAssignedValues(){
        Blackout blackout = new Blackout(true);
        blackout.setId(2);
        blackout.setLights(false);
        Assertions.assertEquals(2, blackout.getId());
        Assertions.assertEquals(false, blackout.isLights());
    }
}