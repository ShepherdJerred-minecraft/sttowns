package com.shepherdjerred.sttowns.commands;

import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.riotbase.commands.SpigotCommandSource;
import com.shepherdjerred.sttowns.commands.registers.PlotNodeRegister;
import com.shepherdjerred.sttowns.commands.subcommands.plot.AbstractPlotCommand;
import com.shepherdjerred.sttowns.commands.subcommands.plot.PlotClaim;
import com.shepherdjerred.sttowns.commands.subcommands.plot.PlotInfo;

public class PlotExecutor extends AbstractPlotCommand {

    public PlotExecutor(PlotNodeRegister register) {
        super(register, new NodeInfo(
                        "plot",
                        "stTowns.plot",
                        "Plot command for stTowns",
                        "/plot <claim>",
                        1,
                        true
                ),
                new PlotClaim(register),
                new PlotInfo(register)
        );
    }

    @Override
    public void execute(SpigotCommandSource sender, String[] args) {
        getChild("help").execute(sender, new String[]{});
    }
}
