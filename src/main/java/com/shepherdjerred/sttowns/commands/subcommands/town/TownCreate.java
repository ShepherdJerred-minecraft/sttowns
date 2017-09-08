package com.shepherdjerred.sttowns.commands.subcommands.town;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.bukkit.Bukkit;

public class TownCreate extends AbstractTownCommand {

    public TownCreate(TownNodeRegister register) {
        super(register, new NodeInfo(
                "create",
                "stTowns.town.create",
                "Create a new town",
                "/town create <name>",
                1,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {
        TownPlayer player = townPlayers.get(sender);

        try {
            townController.createTown(player, args[0]);
        } catch (TrackerException e) {
            sender.sendMessage(parser.colorString(true, "town.exception", "create a town", e.getMessage()));
            e.printStackTrace();
        } catch (CommandException e) {
            sender.sendMessage(e.getPlayerException());
            return;
        }

        // TODO Remove the Bukkit call
        Bukkit.broadcastMessage(parser.colorString(true, "town.create.success.broadcast", args[0], player.getName()));

    }

}
