package com.shepherdjerred.sttowns.commands.subcommands.town;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public class TownLeave extends AbstractTownCommand {

    public TownLeave(TownNodeRegister register) {
        super(register, new NodeInfo(
                "leave",
                "stTowns.town.leave",
                "Leave your town",
                "/town leave",
                0,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer player = townPlayers.get(sender);

        try {
            townController.leaveTown(player);
        } catch (CommandException e) {
            sender.sendMessage(e.getPlayerException());
            return;
        }

    }

}
