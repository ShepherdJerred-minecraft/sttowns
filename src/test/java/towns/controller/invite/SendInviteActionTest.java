package towns.controller.invite;

import com.shepherdjerred.sttowns.exceptions.TownException;
import org.junit.Test;

public class SendInviteActionTest extends AbstractTownInviteActionTest {

    @Test
    public void testSendInvite() throws TownException {
        townPlayer.setTown(town);
        townController.sendInvite(townPlayer, townPlayer2);

        assert townPlayer2.hasTownInvite(town);
    }

    @Test(expected = TownException.class)
    public void testInviterHasNoTown() throws TownException {
        townController.sendInvite(townPlayer, townPlayer2);
    }

}
