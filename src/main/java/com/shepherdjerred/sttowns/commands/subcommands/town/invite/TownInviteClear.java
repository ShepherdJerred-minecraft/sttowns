package com.shepherdjerred.sttowns.commands.subcommands.town.invite;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public class TownInviteClear extends AbstractTownCommand {

    public TownInviteClear(TownNodeRegister register) {
        super(register, new NodeInfo(
                "clear",
                "stTowns.town.invite.clear",
                "Remove invites from all towns",
                "/town invite clear",
                0,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer townPlayer = townPlayers.get(sender);

        townController.clearInvites(townPlayer);

        sender.sendMessage("Invites cleared");

    }

}
