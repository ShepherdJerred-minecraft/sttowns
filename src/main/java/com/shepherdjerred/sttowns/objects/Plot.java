package com.shepherdjerred.sttowns.objects;

import com.shepherdjerred.sttowns.objects.ranks.GroupAction;

import java.util.UUID;

public class Plot extends AbstractEntity {

    private final PlotLocation plotLocation;
    private final Town town;
    private final PlotType plotType;

    public Plot(UUID uuid, String name, PlotLocation plotLocation, Town town, PlotType plotType) {
        super(uuid, name);
        this.plotLocation = plotLocation;
        this.town = town;
        this.plotType = plotType;
    }

    public PlotLocation getPlotLocation() {
        return plotLocation;
    }

    public Town getTown() {
        return town;
    }

    public PlotType getPlotType() {
        return plotType;
    }

    public enum PlotType {
        NORMAL, CENTER
    }

    public enum PlotAction implements GroupAction {
        UNCLAIM, ADD, KICK, SET_RANK, SET_NAME
    }

}
