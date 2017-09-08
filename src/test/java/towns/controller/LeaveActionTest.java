package towns.controller;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.junit.Test;

import java.util.UUID;

public class LeaveActionTest extends AbstractTownActionTest {

    @Test
    public void testLeave() throws TownException {
        townPlayer.setTown(town);
        town.setRank(townPlayer.getUuid(), ranks.getTownRanks().get("members"));

        townController.leaveTown(townPlayer);
    }

    @Test(expected = TownException.class)
    public void testNoTown() throws TownException {
        townController.leaveTown(townPlayer);
    }

    @Test
    public void testRequired() throws TownException {
        TownPlayer townPlayer2 = new TownPlayer(UUID.randomUUID(), "TownPlayer2", null);

        townPlayer.setTown(town);
        town.setRank(townPlayer.getUuid(), ranks.getTownRanks().get("owner"));
        townPlayer2.setTown(town);
        town.setRank(townPlayer2.getUuid(), ranks.getTownRanks().get("owner"));

        townController.leaveTown(townPlayer);
    }

    @Test(expected = TownException.class)
    public void testNoRequired() throws TownException {
        townPlayer.setTown(town);
        town.setRank(townPlayer.getUuid(), ranks.getTownRanks().get("owner"));
        townController.leaveTown(townPlayer);
    }

}
