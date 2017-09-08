package com.shepherdjerred.sttowns.objects.trackers;

import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.PlotLocation;
import com.shepherdjerred.sttowns.objects.Town;
import org.bukkit.Chunk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Plots {

    private final Map<PlotLocation, Plot> plotMap = new HashMap<>();

    public void add(Plot plot) {
        plotMap.put(plot.getPlotLocation(), plot);
    }

    public Plot getPlot(PlotLocation plotLocation) {
        return plotMap.get(plotLocation);
    }

    public Plot getPlot(Chunk chunk) {
        return plotMap.get(new PlotLocation(chunk));
    }

    public boolean contains(PlotLocation plotLocation) {
        return plotMap.containsKey(plotLocation);
    }

    public boolean contains(Chunk chunk) {
        return plotMap.containsKey(new PlotLocation(chunk));
    }

    public boolean isPlotConnected(PlotLocation plotLocation, Town town) {
        UUID world = plotLocation.getWorld();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                // Check if X equals Z, this is to prevent diagonal adjacency from returning true
                if (x == z) {
                    continue;
                }
                int realX = plotLocation.getX() + x;
                int realZ = plotLocation.getZ() + z;
                PlotLocation newLocation = new PlotLocation(realX, realZ, world);
                if (plotMap.containsKey(newLocation)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean closeToOtherTown(PlotLocation plotLocation, Town town, int radius) {
        UUID world = plotLocation.getWorld();
        for (int x = radius * -1; x <= radius; x++) {
            for (int z = radius * -1; z <= radius; z++) {
                int realX = plotLocation.getX() + x;
                int realZ = plotLocation.getZ() + z;
                PlotLocation newLocation = new PlotLocation(realX, realZ, world);
                if (plotMap.containsKey(newLocation)) {
                    Plot plot = getPlot(newLocation);
                    if (plot.getTown() != town) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
