package towns.controller;

import com.shepherdjerred.sttowns.exceptions.TownException;
import org.junit.Test;

public class JoinActionTest extends AbstractTownActionTest {

    @Test
    public void testJoin() throws TownException {
        townController.joinTown(townPlayer, town);
        assert townPlayer.getTownRank() == ranks.getTownRanks().get("members");
        assert townPlayer.getTown() == town;
    }

    @Test(expected = TownException.class)
    public void testAlreadyInTown() throws TownException {
        townPlayer.setTown(town);
        townController.joinTown(townPlayer, town);
    }
}
