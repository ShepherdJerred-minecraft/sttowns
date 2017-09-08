package towns.controller.invite;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.invites.TownInvite;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DeclineInviteActionTest extends AbstractTownInviteActionTest {

    @Before
    public void setup() throws IOException, TrackerException {
        super.setup();
        townPlayer.setTown(town);
    }

    @Test
    public void testDeclineInvite() throws TownException {
        TownInvite invite = new TownInvite(townPlayer, townPlayer2);
        townPlayer2.addInvite(invite);
        townController.declineInvite(townPlayer2, town);
        assert townPlayer2.getTownInvite(town).isDeclined() == true;
    }

    @Test(expected = TownException.class)
    public void testNoInvite() throws TownException {
        townController.declineInvite(townPlayer2, town);
    }

}
