package com.shepherdjerred.sttowns.commands.subcommands.plot;

import com.shepherdjerred.riotbase.commands.CommandNode;
import com.shepherdjerred.riotbase.commands.NodeInfo;
import com.shepherdjerred.sttowns.controllers.PlotController;
import com.shepherdjerred.sttowns.commands.registers.PlotNodeRegister;
import com.shepherdjerred.sttowns.objects.trackers.Plots;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;

public abstract class AbstractPlotCommand extends CommandNode {

    protected final Plots plots;
    protected final TownPlayers townPlayers;
    protected final PlotController plotController;

    public AbstractPlotCommand(PlotNodeRegister register, NodeInfo NodeInfo, AbstractPlotCommand... children) {
        super(register, NodeInfo, children);
        this.plots = register.getPlots();
        this.townPlayers = register.getTownPlayers();
        this.plotController = register.getPlotController();
    }

}
