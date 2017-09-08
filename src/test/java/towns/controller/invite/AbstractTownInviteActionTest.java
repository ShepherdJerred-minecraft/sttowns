package towns.controller.invite;

import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.junit.Before;
import towns.controller.AbstractTownActionTest;

import java.io.IOException;
import java.util.UUID;

public abstract class AbstractTownInviteActionTest extends AbstractTownActionTest {

    protected TownPlayer townPlayer2;

    @Before
    public void setup() throws IOException, TrackerException {
        super.setup();
        townPlayer2 = new TownPlayer(UUID.randomUUID(), "TestPlayer2", null);
    }

}
