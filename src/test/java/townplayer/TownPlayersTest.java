package townplayer;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TownPlayersTest {

    private TownPlayer townPlayer;
    private TownPlayers playerTracker;

    @Before
    public void setup() {
        playerTracker = new TownPlayers();
        townPlayer = new TownPlayer(UUID.randomUUID(), "TestPlayer", null);
    }

    @Test
    public void testGetPlayer() throws TownException, TrackerException {
        UUID playerUuid = townPlayer.getUuid();
        String playerName = townPlayer.getName();

        Player player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(playerUuid);
        when(player.getName()).thenReturn(playerName);

        playerTracker.add(townPlayer);

        assert playerTracker.get(player) == townPlayer;
        assert playerTracker.get((CommandSender) player) == townPlayer;

        playerTracker.remove(townPlayer);

        assert playerTracker.get(player) == null;
        assert playerTracker.get((CommandSender) player) == null;
    }

    @Test
    public void testRemovePlayer() throws TownException, TrackerException {
        UUID playerUuid = townPlayer.getUuid();
        String playerName = townPlayer.getName();

        Player player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(playerUuid);
        when(player.getName()).thenReturn(playerName);

        playerTracker.add(townPlayer);
        playerTracker.remove(player);

        assert playerTracker.get(player) == null;
    }

}
