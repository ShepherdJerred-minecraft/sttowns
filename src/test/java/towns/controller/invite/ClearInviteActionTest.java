package towns.controller.invite;

import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.invites.TownInvite;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ClearInviteActionTest extends AbstractTownInviteActionTest {

    @Before
    public void setup() throws IOException, TrackerException {
        super.setup();
        townPlayer.setTown(town);
        townPlayers.add(townPlayer);
        townPlayers.add(townPlayer2);
    }

    @Test
    public void testClearInvite() {
        townPlayer2.addInvite(new TownInvite(townPlayer, townPlayer2));
        townController.clearInvites(townPlayer2);
        assert townPlayer2.getInvitesAsList().size() == 0;
        assert townPlayer2.hasTownInvite(town) == false;
    }

}
