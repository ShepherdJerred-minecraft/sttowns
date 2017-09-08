package towns.commands;

import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.subcommands.town.TownDestroy;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DestroyTest extends AbstractTownCommandTest {

    @Before
    public void setup() throws TrackerException, IOException {
        super.setup();
        command = new TownDestroy(townNodeRegister);
    }

    @Test
    public void testDestroy() {
        townPlayer.setTown(town);
        town.setRank(townPlayer.getUuid(), ranks.getTownRanks().get("owner"));

        OfflinePlayer offlinePlayer = mock(OfflinePlayer.class);
        when(offlinePlayer.isOnline()).thenReturn(true);

        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.broadcastMessage(anyString())).thenReturn(1);
        when(Bukkit.getOfflinePlayer(townPlayer.getUuid())).thenReturn(offlinePlayer);

        command.execute(new SpigotCommandSource(player), new String[]{});

        assert townPlayer.hasTown() == false;
        assert towns.contains(town) == false;
    }

    @Test
    public void testNotInTown() {
        command.execute(new SpigotCommandSource(player), new String[]{});
    }

    @Test
    public void testNoTownPermission() {
        townPlayer.setTown(town);
        town.setRank(townPlayer.getUuid(), ranks.getTownRanks().get("members"));

        command.execute(new SpigotCommandSource(player), new String[]{});

        assert townPlayer.getTown() == town;
    }

}
