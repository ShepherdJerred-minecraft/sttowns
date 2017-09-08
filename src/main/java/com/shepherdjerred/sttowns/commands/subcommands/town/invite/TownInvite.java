package com.shepherdjerred.sttowns.commands.subcommands.town.invite;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;

public class TownInvite extends AbstractTownCommand {

    public TownInvite(TownNodeRegister register) {
        super(register, new NodeInfo(
                        "invite",
                        "stTowns.town.invite",
                        "Manage towns invites",
                        "/town invite <accept, clear, decline, list, revoke, send>",
                        1,
                        false
                ),
                new TownInviteAccept(register),
                new TownInviteClear(register),
                new TownInviteDecline(register),
                new TownInviteList(register),
                new TownInviteRevoke(register),
                new TownInviteSend(register)
        );
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {
        getChild("help").execute(sender, new String[]{});
    }

}
