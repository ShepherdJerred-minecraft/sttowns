package com.shepherdjerred.sttowns.commands.subcommands.town.invite;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;
import com.shepherdjerred.sttowns.objects.TownPlayer;

public class TownInviteList extends AbstractTownCommand {

    public TownInviteList(TownNodeRegister register) {
        super(register, new NodeInfo(
                "list",
                "stTowns.town.invite.list",
                "List invites from all towns",
                "/town invite list",
                0,
                false
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {

        TownPlayer townPlayer = townPlayers.get(sender);

        sender.sendMessage("Town invites:");
        townPlayer.getInvitesAsList().forEach(invite -> sender.sendMessage(invite.getInvitedTo().getName()));

    }

}
