package com.shepherdjerred.sttowns.database;

import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.PlotLocation;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.Mapper;
import org.codejargon.fluentjdbc.api.query.Query;

import java.util.List;
import java.util.UUID;

public class PlotDAO {

    private final FluentJdbc fluentJdbc;
    private final Entities<Town> towns;

    public PlotDAO(FluentJdbc fluentJdbc, Entities<Town> towns) {
        this.fluentJdbc = fluentJdbc;
        this.towns = towns;
    }

    public void insert(Plot plot) {
        Query query = fluentJdbc.query();

        query
                .update("INSERT INTO plots VALUES (?,?,?,?,?,?,?)")
                .params(
                        plot.getUuid().toString(),
                        plot.getName(),
                        plot.getPlotType().toString(),
                        plot.getTown().getUuid().toString(),
                        plot.getPlotLocation().getX(),
                        plot.getPlotLocation().getZ(),
                        plot.getPlotLocation().getWorld().toString()
                ).run();
    }

    public List<Plot> loadAll() {
        Mapper<Plot> plotMapper = rs -> new Plot(
                UUID.fromString(rs.getString("plot_uuid")),
                rs.getString("name"),
                new PlotLocation(
                        rs.getInt("x"),
                        rs.getInt("z"),
                        UUID.fromString(rs.getString("world"))
                ),
                towns.get(UUID.fromString("town_uuid")),
                Plot.PlotType.valueOf("plot_type")
        );

        Query query = fluentJdbc.query();

        List<Plot> plots = query
                .select("SELECT * FROM plots")
                .listResult(plotMapper);

        return plots;
    }

}
