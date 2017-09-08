package townplayer;

import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.invites.TownInvite;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TownPlayerTest {

    private TownPlayer townPlayer;
    private Town town;

    @Before
    public void setup() {
        townPlayer = new TownPlayer(UUID.randomUUID(), "TestPlayer", null);
        town = new Town(UUID.randomUUID(), "TestTown", "MOTD");
    }

    @Test
    public void testPlayerCreation() {
        UUID playerUuid = UUID.randomUUID();
        String playerName = "TestPlayer";

        Player player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(playerUuid);
        when(player.getName()).thenReturn(playerName);

        TownPlayer townPlayer1 = new TownPlayer(player);

        assert townPlayer1.getName().equalsIgnoreCase(playerName);
        assert townPlayer1.getUuid().equals(playerUuid);

        TownPlayer townPlayer2 = new TownPlayer(playerUuid, playerName, null);
    }

    @Test
    public void getTownTest() {
        assert townPlayer.getTown() == null;
        townPlayer.setTown(town);
        assert townPlayer.getTown() == town;
    }

    @Test
    public void testHasTown() {
        assert townPlayer.hasTown() == false;
        townPlayer.setTown(town);
        assert townPlayer.hasTown() == true;
    }

    @Test
    public void testPlayerJoiningTown() {
        townPlayer.setTown(town);

        assert townPlayer.getTown() == town;
    }

    @Test
    public void testTownInvite() {
        TownInvite townInvite = new TownInvite(new TownPlayer(UUID.randomUUID(), "TestPlayer2", town), townPlayer);

        assert townPlayer.hasTownInvite(town) == false;

        townPlayer.addInvite(townInvite);

        assert townPlayer.hasTownInvite(town) == true;
    }

    @Test
    public void testToString() {
        assert townPlayer.toString() != null;
    }

}
