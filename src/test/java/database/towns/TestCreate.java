package database.towns;

import com.shepherdjerred.sttowns.database.TownDAO;
import com.shepherdjerred.sttowns.objects.Town;
import database.AbstractDatabaseTest;
import org.junit.Test;

import java.util.UUID;

public class TestCreate extends AbstractDatabaseTest {

    @Test
    public void testCreate() {
        UUID townUuid = UUID.randomUUID();
        new TownDAO(fluentJdbc, rankManager).insert(new Town(townUuid, "TestTown", "MOTD"));
    }

}
