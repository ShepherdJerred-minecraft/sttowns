package com.shepherdjerred.sttowns.commands.subcommands.town.set;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public class TownSetName extends AbstractTownCommand {

    public TownSetName(TownNodeRegister register) {
        super(register, new NodeInfo(
                "name",
                "stTowns.town.set.name",
                "Set the name of the town",
                "/town set name <name>",
                1,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer player = townPlayers.get(sender);

        try {
            townController.setName(player, args[0]);
        } catch (CommandException e) {
            e.printStackTrace();
        }

        sender.sendMessage(parser.colorString(true, "town.set.name.online", player.getTown().getName()));
        // TODO Send the new name to all online players

    }

}
