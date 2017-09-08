package towns;

import com.shepherdjerred.sttowns.objects.Nation;
import com.shepherdjerred.sttowns.objects.Town;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class TownTest {

    private Town town;

    @Before
    public void setup() {
        town = new Town(UUID.randomUUID(), "TestTown", "MOTD");
    }

    @Test
    public void testTownCreation() {
        UUID townUuid = UUID.randomUUID();
        String townName = "TestTown";
        String townMotd = "MOTD";

        Town town1 = new Town(townUuid, townName, townMotd);

        assert town1.getUuid().equals(townUuid);
        assert town1.getName().equals(townName);
        assert town1.getMotd().equals(townMotd);

        Town town2 = new Town(townUuid, townName, townMotd, false, null);

        assert town2.getUuid().equals(townUuid);
        assert town2.getName().equals(townName);
        assert town2.getMotd().equals(townMotd);
        assert town2.isOpen() == false;
        assert town2.getNation() == null;

    }

    @Test
    public void testTownNation() {
        Nation nation = new Nation(UUID.randomUUID(), "TestNation", "MOTD");

        town.setNation(nation);
        assert town.getNation() == nation;
    }

    @Test
    public void testToString() {
        assert town.toString() != null;
    }

}
