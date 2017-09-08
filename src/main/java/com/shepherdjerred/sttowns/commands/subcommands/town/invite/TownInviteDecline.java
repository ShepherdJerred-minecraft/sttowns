package com.shepherdjerred.sttowns.commands.subcommands.town.invite;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;
import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public class TownInviteDecline extends AbstractTownCommand {

    public TownInviteDecline(TownNodeRegister register) {
        super(register, new NodeInfo(
                "decline",
                "stTowns.town.invite.decline",
                "Decline an invitation to join a towns",
                "/town invite decline <towns name>",
                1,
                false
        ));

    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer townPlayer = townPlayers.get(sender);

        if (townPlayer.hasTown()) {
            sender.sendMessage(parser.colorString(true, "town.alreadyInTown"));
            return;
        }

        if (!towns.contains(args[0])) {
            sender.sendMessage(parser.colorString(true, "town.townDoesntExist", args[0]));
            return;
        }

        Town town = towns.get(args[0]);

        if (!townPlayer.hasTownInvite(town)) {
            sender.sendMessage(parser.colorString(true, "town.invite.accept.noInvite", town.getName()));
            return;
        }

        try {
            townController.declineInvite(townPlayer, town);
        } catch (TownException e) {
            sender.sendMessage(parser.colorString(true, "town.exception", "accepting a towns invite", e.getMessage()));
            e.printStackTrace();
        }

    }

}
