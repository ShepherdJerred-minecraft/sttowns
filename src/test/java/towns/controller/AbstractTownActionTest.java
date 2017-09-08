package towns.controller;

import com.shepherdjerred.sttowns.controllers.TownController;
import com.shepherdjerred.sttowns.database.TownDAO;
import com.shepherdjerred.sttowns.database.TownInviteDAO;
import com.shepherdjerred.sttowns.database.TownPlayerDAO;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.messages.Parser;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.ranks.Ranks;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;
import org.apache.commons.io.FileUtils;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.UUID;

public abstract class AbstractTownActionTest {

    protected Parser parser = new Parser(ResourceBundle.getBundle("messages"));

    protected Entities<Town> towns;
    protected TownPlayers townPlayers;
    protected TownController townController;
    protected TownPlayer townPlayer;
    protected Town town;
    protected Ranks ranks;
    protected TownDAO townDAO;
    protected TownPlayerDAO townPlayerDAO;
    protected TownInviteDAO townInviteDAO;

    @Before
    public void setup() throws TrackerException, IOException {
        // Create trackers and other utilities
        towns = new Entities<>();
        townPlayers = new TownPlayers();
        ranks = new Ranks();
        townController = new TownController(parser, towns, townPlayers, ranks, townDAO, townPlayerDAO, townInviteDAO);

        // Get File
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("towns.json").getFile());

        // Load ranks
        ranks.loadTowns(FileUtils.readFileToString(file));

        // Create objects
        townPlayer = new TownPlayer(UUID.randomUUID(), "TestPlayer", null);
        town = new Town(UUID.randomUUID(), "TestTown", "MOTD");
    }

}
