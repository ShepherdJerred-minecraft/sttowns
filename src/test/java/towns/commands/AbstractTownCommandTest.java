package towns.commands;

import com.shepherdjerred.riotbase.commands.CommandNode;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PrepareForTest(Bukkit.class)
@RunWith(PowerMockRunner.class)
public abstract class AbstractTownCommandTest {

    protected Parser parser = new Parser(ResourceBundle.getBundle("messages"));

    protected TownPlayer townPlayer;
    protected Player player;
    protected TownPlayers townPlayers;
    protected Ranks ranks;
    protected Entities<Town> towns;
    protected TownController townController;
    protected Town town;
    protected CommandNode command;
    protected TownNodeRegister townNodeRegister;
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

        townNodeRegister = new TownNodeRegister(parser, towns, townPlayers, townController);

        // Get File
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("towns.json").getFile());

        // Load ranks
        ranks.loadTowns(FileUtils.readFileToString(file));

        // Create objects
        townPlayer = new TownPlayer(UUID.randomUUID(), "TestPlayer", null);
        town = new Town(UUID.randomUUID(), "TestTown", "MOTD");

        // Add objects to trackers
        townPlayers.add(townPlayer);
        towns.add(town);

        // Create mock player
        player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(townPlayer.getUuid());

        // Mock bukkit broadcastMessage
        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.broadcastMessage(anyString())).thenReturn(1);

    }

}
