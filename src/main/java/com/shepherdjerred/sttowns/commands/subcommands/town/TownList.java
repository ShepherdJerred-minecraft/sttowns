package com.shepherdjerred.sttowns.commands.subcommands.town;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;

public class TownList extends AbstractTownCommand {

    public TownList(TownNodeRegister register) {
        super(register, new NodeInfo(
                "list",
                "stTowns.town.list",
                "Shows a list of all towns on the server",
                "/town list",
                0,
                true
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {
        sender.sendMessage(parser.colorString(false, "town.list.header"));
        towns.getAsList().forEach(town -> sender.sendMessage(parser.colorString(false, "town.list.line", town.getName())));
    }

}
