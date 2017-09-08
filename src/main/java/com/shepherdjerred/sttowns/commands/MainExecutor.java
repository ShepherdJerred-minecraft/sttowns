package com.shepherdjerred.sttowns.commands;

import com.shepherdjerred.riotbase.commands.CommandNode;
import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.NodeRegister;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;

public class MainExecutor extends CommandNode {

    public MainExecutor(NodeRegister register) {
        super(register, new NodeInfo (
                "sttowns",
                "stTowns.main",
                "Main command for stTowns",
                "/sttowns <help, reload>",
                1,
                true
        ));
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {
        getChild("help").execute(sender, new String[]{});
    }
}
