package com.shepherdjerred.sttowns.commands.subcommands.town.set;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public class TownSetOpen extends AbstractTownCommand {

    public TownSetOpen(TownNodeRegister register) {
        super(register, new NodeInfo(
                "open",
                "stTowns.town.set.open",
                "Set the public status of the towns",
                "/town set open <true/false>",
                1,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer player = townPlayers.get(sender);

        boolean open;

        if (args[0].equalsIgnoreCase("true")) {
            open = true;
        } else if (args[0].equalsIgnoreCase("false")) {
            open = false;
        } else {
            sender.sendMessage(parser.colorString(true, "generic.invalidArgument", nodeInfo.getUsage()));
            return;
        }

        try {
            townController.setOpen(player, open);
        } catch (CommandException e) {
            sender.sendMessage(e.getPlayerException());
            return;
        }

        if (open) {
            sender.sendMessage(parser.colorString(true, "town.set.open.true.online"));
        } else {
            sender.sendMessage(parser.colorString(true, "town.set.open.false.online"));
        }

    }

}
