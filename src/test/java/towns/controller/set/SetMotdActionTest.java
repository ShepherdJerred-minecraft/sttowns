package towns.controller.set;

import com.shepherdjerred.sttowns.exceptions.TrackerException;
import org.junit.Before;
import org.junit.Test;
import towns.controller.AbstractTownActionTest;

import java.io.IOException;

public class SetMotdActionTest extends AbstractTownActionTest {

    @Before()
    public void setup() throws TrackerException, IOException {
        super.setup();
        towns.add(town);
    }

    @Test
    public void testSetMotd() {
        townController.setMotd(town, "New MOTD");
        assert town.getMotd().equals("New MOTD");
    }

}
