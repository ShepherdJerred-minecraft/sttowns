package com.shepherdjerred.sttowns.database;

import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.ranks.Rank;
import com.shepherdjerred.sttowns.objects.ranks.Ranks;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.Mapper;
import org.codejargon.fluentjdbc.api.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TownDAO {

    private final FluentJdbc fluentJdbc;
    private final Ranks ranks;

    public TownDAO(FluentJdbc fluentJdbc, Ranks ranks) {
        this.fluentJdbc = fluentJdbc;
        this.ranks = ranks;
    }

    public void setName(Town town) {
        Query query = fluentJdbc.query();

        query
                .update("UPDATE towns SET name = ? WHERE town_uuid = ?")
                .params(
                        town.getName(),
                        town.getUuid().toString()
                ).run();

    }

    public void setMotd(Town town) {
        Query query = fluentJdbc.query();

        query
                .update("UPDATE towns SET motd = ? WHERE town_uuid = ?")
                .params(
                        town.getMotd(),
                        town.getUuid().toString()
                ).run();
    }

    public void setOpen(Town town) {
        Query query = fluentJdbc.query();

        query
                .update("UPDATE towns SET open = ? WHERE town_uuid = ?")
                .params(
                        town.isOpen(),
                        town.getUuid().toString()
                ).run();
    }

    public void setNation(Town town) {

    }

    public void setPlotBank(Town town, Plot.PlotType type) {
        Query query = fluentJdbc.query();

        query
                .update("UPDATE town_plot_bank SET amount = ? WHERE town_uuid = ? AND plot_type = ?")
                .params(
                        town.getRemainingPlots(type),
                        town.getUuid().toString(),
                        type.toString()
                ).run();
    }

    public void drop(Town town) {
        Query query = fluentJdbc.query();
        query
                .update("DELETE FROM towns WHERE town_uuid = ?")
                .params(
                        town.getUuid().toString()
                ).run();
    }

    public void insert(Town town) {
        Query query = fluentJdbc.query();

        query
                .update("INSERT INTO towns VALUES (?,?,?,?)")
                .params(
                        town.getUuid().toString(),
                        town.getName(),
                        town.getMotd(),
                        town.isOpen()
                ).run();

        insertPlotBank(town);

    }

    private void insertPlotBank(Town town) {

        Query query = fluentJdbc.query();

        town.getRemainingPlots().forEach((plotType, integer) -> query
                .update("INSERT INTO town_plot_bank VALUES (?,?,?)")
                .params(
                        town.getUuid().toString(),
                        plotType,
                        integer
                ).run());

    }

    private Map<Plot.PlotType, Integer> loadPlotBank(Town town) {

        Map<Plot.PlotType, Integer> returnMap = new HashMap<>();

        Mapper<Map<Plot.PlotType, Integer>> plotMapper = rs -> {
            Map<Plot.PlotType, Integer> plotMap = new HashMap<>();

            Plot.PlotType plotType = Enum.valueOf(Plot.PlotType.class, rs.getString("plot_type"));
            Integer amount = rs.getInt("amount");

            plotMap.put(plotType, amount);

            return plotMap;
        };

        Query query = fluentJdbc.query();

        List<Map<Plot.PlotType, Integer>> plots = query
                .select("SELECT * FROM town_plot_bank WHERE town_uuid = ?")
                .params(
                        town.getUuid().toString()
                ).listResult(plotMapper);

        plots.forEach(returnMap::putAll);

        return returnMap;

    }

    private Map<UUID, Rank<Town.TownAction>> loadMembers(Town town) {

        Map<UUID, Rank<Town.TownAction>> returnMap = new HashMap<>();

        Mapper<Map<UUID, Rank<Town.TownAction>>> rankMapper = rs -> {
            Map<UUID, Rank<Town.TownAction>> rankMap = new HashMap<>();

            UUID playerUuid = UUID.fromString(rs.getString("player_uuid"));
            Rank<Town.TownAction> playerRank = ranks.getTownRanks().get(rs.getString("rank"));

            rankMap.put(playerUuid, playerRank);

            System.out.println("Loaded " + playerUuid + " with rank " + playerRank.getName());

            return rankMap;
        };

        Query query = fluentJdbc.query();

        List<Map<UUID, Rank<Town.TownAction>>> players = query
                .select("SELECT * FROM town_player_towns WHERE town_uuid = ?")
                .params(
                        town.getUuid().toString()
                ).listResult(rankMapper);

        players.forEach(returnMap::putAll);

        return returnMap;

    }

    public List<Town> loadAll() {

        // TODO Load nation
        Mapper<Town> townMapper = rs -> new Town(
                UUID.fromString(rs.getString("town_uuid")),
                rs.getString("name"),
                rs.getString("motd"),
                rs.getBoolean("open"),
                null
        );

        Query query = fluentJdbc.query();

        List<Town> towns = query
                .select("SELECT * FROM towns")
                .listResult(townMapper);

        towns.forEach(town -> {
            Map<Plot.PlotType, Integer> plots = loadPlotBank(town);
            plots.forEach(town::setRemainingPlots);

            Map<UUID, Rank<Town.TownAction>> members = loadMembers(town);
            members.forEach(town::setRank);
        });

        return towns;

    }

}
