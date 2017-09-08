package com.shepherdjerred.sttowns.commands.registers;

import com.shepherdjerred.riotbase.commands.NodeRegister;
import com.shepherdjerred.riotbase.messages.AbstractParser;
import com.shepherdjerred.sttowns.controllers.PlotController;
import com.shepherdjerred.sttowns.objects.trackers.Plots;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;

public class PlotNodeRegister extends NodeRegister {

    private final Plots plots;
    private final TownPlayers townPlayers;
    private final PlotController plotController;

    public PlotNodeRegister(AbstractParser parser, Plots plots, TownPlayers townPlayers, PlotController plotController) {
        super(parser);
        this.plots = plots;
        this.townPlayers = townPlayers;
        this.plotController = plotController;
    }

    public Plots getPlots() {
        return plots;
    }

    public TownPlayers getTownPlayers() {
        return townPlayers;
    }

    public PlotController getPlotController() {
        return plotController;
    }
}
