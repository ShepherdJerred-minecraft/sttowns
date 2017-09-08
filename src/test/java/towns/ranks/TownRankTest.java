package towns.ranks;

import com.shepherdjerred.sttowns.controllers.TownController;
import com.shepherdjerred.sttowns.database.TownDAO;
import com.shepherdjerred.sttowns.database.TownInviteDAO;
import com.shepherdjerred.sttowns.database.TownPlayerDAO;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.messages.Parser;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.ranks.Ranks;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class TownRankTest {

    protected Parser parser = new Parser(ResourceBundle.getBundle("messages"));

    protected Entities<Town> towns;
    protected TownPlayers townPlayers;
    protected TownController townController;
    protected Ranks ranks;
    protected TownDAO townDAO;
    protected TownPlayerDAO townPlayerDAO;
    protected TownInviteDAO townInviteDAO;

    @Before
    public void setup() {
        towns = new Entities<>();
        townPlayers = new TownPlayers();
        ranks = new Ranks();
        townController = new TownController(parser, towns, townPlayers, ranks, townDAO, townPlayerDAO, townInviteDAO);
    }

    @Test
    public void testLoad() throws TrackerException, IOException {
        // Get File
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("towns.json").getFile());

        // Load ranks
        ranks.loadTowns(FileUtils.readFileToString(file));

        assert ranks.getTownRanks().get("members") != null;
        assert ranks.getTownRanks().get("assistants") != null;
        assert ranks.getTownRanks().get("owner") != null;
        assert ranks.getTownRanks().getAsList().size() > 0;
    }

}
