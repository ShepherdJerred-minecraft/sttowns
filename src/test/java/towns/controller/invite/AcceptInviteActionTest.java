package towns.controller.invite;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AcceptInviteActionTest extends AbstractTownInviteActionTest {

    @Before
    public void setup() throws IOException, TrackerException {
        super.setup();
        townPlayer.setTown(town);
        townPlayers.add(townPlayer);
        townPlayers.add(townPlayer2);
    }

    @Test
    public void testAcceptInvite() throws TownException, TrackerException {
        townController.sendInvite(townPlayer, townPlayer2);
        townController.acceptInvite(townPlayer2, town);
        assert townPlayer2.getTown() == town;
    }

    @Test(expected = TownException.class)
    public void testNoInvite() throws TownException {
        townController.acceptInvite(townPlayer2, town);
    }

}
