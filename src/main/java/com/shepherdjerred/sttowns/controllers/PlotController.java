package com.shepherdjerred.sttowns.controllers;

import com.shepherdjerred.riotbase.exceptions.CommandException;
import com.shepherdjerred.sttowns.database.PlotDAO;
import com.shepherdjerred.sttowns.database.TownDAO;
import com.shepherdjerred.sttowns.messages.Parser;
import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.PlotLocation;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.trackers.Plots;

import java.util.UUID;

public class PlotController {

    private final Parser parser;
    private final Plots plots;
    private final TownDAO townDAO;
    private final PlotDAO plotDAO;

    public PlotController(Parser parser, Plots plots, TownDAO townDAO, PlotDAO plotDAO) {
        this.parser = parser;
        this.plots = plots;
        this.townDAO = townDAO;
        this.plotDAO = plotDAO;
    }

    public void createPlot(TownPlayer claimingPlayer, PlotLocation claimingLocation, Plot.PlotType plotType) throws CommandException {
        Town town = claimingPlayer.getTown();

        if (town == null) {
            throw new CommandException("No town", "No Town");
        }

        if (!claimingPlayer.hasPermission(Town.TownAction.CLAIM)) {
            throw new CommandException("No permission to claim", "No permission to claim");
        }

        if (!town.canClaimMore(plotType)) {
            throw new CommandException("Town can't claim more", "Town can't claim more");
        }

        if (plots.contains(claimingLocation)) {
            throw new CommandException("Location already claimed", "Location already claimed");
        }

        if (plotType != Plot.PlotType.CENTER && !plots.isPlotConnected(claimingLocation, town)) {
            throw new CommandException("Plot isn't connected", "Plot isn't connected");
        }

        // TODO Radius check should depend on plot type
        if (plots.closeToOtherTown(claimingLocation, town, 3)) {
            throw new CommandException("Plot too close to other towns", "Plot too close to other towns");
        }

        int currentPlots = town.getRemainingPlots(plotType);
        town.setRemainingPlots(plotType, currentPlots - 1);

        Plot claimedPlot = new Plot(UUID.randomUUID(), null, claimingLocation, town, plotType);
        plots.add(claimedPlot);

        // TODO Add to database
        townDAO.setPlotBank(town, plotType);
        plotDAO.insert(claimedPlot);

    }

}
