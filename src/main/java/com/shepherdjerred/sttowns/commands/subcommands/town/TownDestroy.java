package com.shepherdjerred.sttowns.commands.subcommands.town;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import org.bukkit.Bukkit;

public class TownDestroy extends AbstractTownCommand {

    public TownDestroy(TownNodeRegister register) {
        super(register, new NodeInfo(
                "destroy",
                "stTowns.town.destroy",
                "Destroy your town",
                "/town destroy",
                0,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {
        // TODO Confirm action

        TownPlayer player = townPlayers.get(sender);
        String townName = player.getTown().getName();

        try {
            townController.destroyTown(player);
        } catch (CommandException e) {
            sender.sendMessage(e.getPlayerException());
            return;
        }

        // TODO Don't call the Bukkit API directly
        Bukkit.broadcastMessage(parser.colorString(true, "town.destroy", townName, player.getName()));

    }

}
