package towns.controller.set;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.Town;
import org.junit.Before;
import org.junit.Test;
import towns.controller.AbstractTownActionTest;

import java.io.IOException;
import java.util.UUID;

public class SetNameActionTest extends AbstractTownActionTest {

    @Before()
    public void setup() throws TrackerException, IOException {
        super.setup();
        towns.add(town);
    }

    @Test
    public void testSetName() throws TownException {
        townController.setName(town, "New Name");
        assert town.getName().equals("New Name");
    }

    @Test(expected = TownException.class)
    public void testShortName() throws TownException {
        townController.setName(town, "12");
    }

    @Test(expected = TownException.class)
    public void testLongName() throws TownException {
        townController.setName(town, "SomeReallyLongTownName");
    }

    @Test(expected = TownException.class)
    public void testTownNameTaken() throws TownException, TrackerException {
        towns.add(new Town(UUID.randomUUID(), "ThisNameIsTaken", "MOTD"));
        townController.setName(town, "ThisNameIsTaken");
    }

}
