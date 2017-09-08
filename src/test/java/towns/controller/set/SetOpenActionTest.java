package towns.controller.set;

import com.shepherdjerred.sttowns.exceptions.TrackerException;
import org.junit.Before;
import org.junit.Test;
import towns.controller.AbstractTownActionTest;

import java.io.IOException;

public class SetOpenActionTest extends AbstractTownActionTest {

    @Before()
    public void setup() throws TrackerException, IOException {
        super.setup();
        towns.add(town);
    }

    @Test
    public void testSetOpenTrue() {
        townController.setOpen(town, true);
        assert town.isOpen() == true;
    }

    @Test
    public void testSetOpenFalse() {
        townController.setOpen(town, false);
        assert town.isOpen() == false;
    }

}
