package towns.commands;

import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.subcommands.town.TownJoin;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.Town;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.io.IOException;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JoinTest extends AbstractTownCommandTest {

    @Before
    public void setup() throws TrackerException, IOException {
        super.setup();
        command = new TownJoin(townNodeRegister);
    }

    @Test
    public void testJoinCommand() {
        town.setOpen(true);

        Player player = mock(Player.class);
        when(player.isOnline()).thenReturn(true);
        when(player.getUniqueId()).thenReturn(townPlayer.getUuid());

        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.getOfflinePlayer(townPlayer.getUuid())).thenReturn(player);
        when(Bukkit.getPlayer(townPlayer.getUuid())).thenReturn(player);

        command.execute(new SpigotCommandSource(player), new String[]{"TestTown"});

        assert townPlayer.getTown() == town;
    }

    @Test
    public void testAlreadyInTown() throws TrackerException {
        Town town2 = new Town(UUID.randomUUID(), "TestTown2", "MOTD");
        towns.add(town2);

        townPlayer.setTown(town);
        command.execute(new SpigotCommandSource(player), new String[]{"TestTown2"});
    }

    @Test
    public void testTownNotOpen() {
        command.execute(new SpigotCommandSource(player), new String[]{"TestTown"});
    }

    @Test
    public void testTownDoesntExist() {
        command.execute(new SpigotCommandSource(player), new String[]{"SomeOtherTown"});
    }

}
