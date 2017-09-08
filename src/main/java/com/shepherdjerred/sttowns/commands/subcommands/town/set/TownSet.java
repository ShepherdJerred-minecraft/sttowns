package com.shepherdjerred.sttowns.commands.subcommands.town.set;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.town.AbstractTownCommand;

public class TownSet extends AbstractTownCommand {

    public TownSet(TownNodeRegister register) {
        super(register, new NodeInfo(
                        "set",
                        "stTowns.town.set",
                        "Set properties of the town",
                        "/town set <name, motd, open>",
                        1,
                        false
                ),
                new TownSetMotd(register),
                new TownSetName(register),
                new TownSetOpen(register)
        );
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {
        getChild("help").execute(sender, new String[]{});
    }

}
