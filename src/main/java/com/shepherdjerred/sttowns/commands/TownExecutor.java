package com.shepherdjerred.sttowns.commands;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.*;
import com.shepherdjerred.sttowns.commands.subcommands.town.invite.TownInvite;
import com.shepherdjerred.sttowns.commands.subcommands.town.set.TownSet;

public class TownExecutor extends AbstractTownCommand {

    public TownExecutor(TownNodeRegister register) {
        super(register, new NodeInfo(
                        "town",
                        "stTowns.town",
                        "Town command for stTowns",
                        "/town <claim, create, destroy, info, invite, join, kick, leave, list, set>",
                        1,
                        true
                ),
                new TownCreate(register),
                new TownInfo(register),
                new TownList(register),
                new TownDestroy(register),
                new TownInvite(register),
                new TownJoin(register),
                new TownLeave(register),
                new TownKick(register),
                new TownSet(register)
        );
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {
        getChild("help").execute(sender, new String[]{});
    }
}
