package towns.controller;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import org.junit.Test;

public class CreateActionTest extends AbstractTownActionTest {

    @Test
    public void testCreation() throws TownException, TrackerException {
        String name = "TestTown";
        townController.createTown(townPlayer, name);

        assert townPlayer.hasTown();
        assert townPlayer.getTown().getName().equals(name);
        assert townPlayer.getTownRank() != null;
        assert townPlayer.getTownRank().getName().equals("owner");
        assert towns.contains(townPlayer.getTown());
        assert towns.getAsList().size() == 1;
        assert townPlayer.getTown().getMotd().equals(parser.colorString(false, "town.defaultMotd"));
    }

    @Test(expected = TownException.class)
    public void testShortName() throws TownException, TrackerException {
        townController.createTown(townPlayer, "12");
    }

    @Test(expected = TownException.class)
    public void testLongName() throws TrackerException, TownException {
        townController.createTown(townPlayer, "SomeReallyLongTownName");
    }

    @Test(expected = TownException.class)
    public void testAlreadyInTown() throws TownException, TrackerException {
        townPlayer.setTown(town);
        townController.createTown(townPlayer, "TestTown");
    }

    @Test(expected = TownException.class)
    public void testTownNameTaken() throws TownException, TrackerException {
        towns.add(town);
        townController.createTown(townPlayer, "TestTown");
    }

}
