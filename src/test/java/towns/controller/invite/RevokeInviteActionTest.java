package towns.controller.invite;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.invites.TownInvite;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class RevokeInviteActionTest extends AbstractTownInviteActionTest {

    @Before
    public void setup() throws IOException, TrackerException {
        super.setup();
        townPlayer.setTown(town);
    }

    @Test
    public void testRevokeInvite() throws TownException {
        TownInvite invite = new TownInvite(townPlayer, townPlayer2);
        townPlayer2.addInvite(invite);
        townController.revokeInvite(townPlayer2, town);
        assert townPlayer2.hasTownInvite(town) == false;
    }

    @Test(expected = TownException.class)
    public void testNoInvite() throws TownException {
        townController.revokeInvite(townPlayer2, town);
    }

}
