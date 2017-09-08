package towns.controller;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PrepareForTest(Bukkit.class)
@RunWith(PowerMockRunner.class)
public class DestroyActionTest extends AbstractTownActionTest {

    @Test
    public void testDestroy() throws TrackerException, TownException {
        townPlayers.add(townPlayer);
        townController.createTown(townPlayer, "TestTown");

        OfflinePlayer offlinePlayer = mock(OfflinePlayer.class);
        when(offlinePlayer.isOnline()).thenReturn(true);

        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.broadcastMessage(anyString())).thenReturn(1);
        when(Bukkit.getOfflinePlayer(townPlayer.getUuid())).thenReturn(offlinePlayer);

        townController.destroyTown(townPlayer.getTown());

        assert townPlayer.hasTown() == false;
    }

}
