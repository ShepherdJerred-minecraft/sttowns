package com.shepherdjerred.sttowns.commands.subcommands.town.set;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;
import com.shepherdjerred.sttowns.objects.TownPlayer;

import java.util.Arrays;

public class TownSetMotd extends AbstractTownCommand {

    public TownSetMotd(TownNodeRegister register) {
        super(register, new NodeInfo(
                "motd",
                "stTowns.town.set.motd",
                "Set the motd of the town",
                "/town set motd <new motd>",
                1,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer player = townPlayers.get(sender);

        try {
            townController.setMotd(player, Arrays.toString(args));
        } catch (CommandException e) {
            sender.sendMessage(e.getPlayerException());
            return;
        }

        sender.sendMessage(parser.colorString(true, "town.set.motd.online", player.getTown().getMotd()));
        // TODO Send the new MOTD to all online players

    }

}
