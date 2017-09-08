package towns.commands;

import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.subcommands.town.TownCreate;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class CreateTest extends AbstractTownCommandTest {

    @Before
    public void setup() throws TrackerException, IOException {
        super.setup();
        command = new TownCreate(townNodeRegister);
    }

    @Test
    public void testCreate() {

        command.execute(new SpigotCommandSource(player), new String[]{"TestTown2"});

        assert townPlayer.getTown().getName().equals("TestTown2");
    }

    @Test
    public void testAlreadyInTown() {
        townPlayer.setTown(town);

        command.execute(new SpigotCommandSource(player), new String[]{"TestTown2"});

        assert townPlayer.getTown().getName().equals("TestTown");
    }

    @Test
    public void testNameTaken() {
        command.execute(new SpigotCommandSource(player), new String[]{"TestTown"});

        assert townPlayer.hasTown() == false;
    }

    @Test
    public void testShortName() {
        command.execute(new SpigotCommandSource(player), new String[]{"12"});

        assert townPlayer.hasTown() == false;
    }

    @Test
    public void testLongName() {
        command.execute(new SpigotCommandSource(player), new String[]{"SomeReallyLongTownName"});

        assert townPlayer.hasTown() == false;
    }

}
