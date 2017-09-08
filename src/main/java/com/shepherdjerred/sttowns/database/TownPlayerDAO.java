package com.shepherdjerred.sttowns.database;

import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.TownPlayer;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import org.bukkit.entity.Player;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.Mapper;
import org.codejargon.fluentjdbc.api.query.Query;

import java.util.Optional;
import java.util.UUID;

public class TownPlayerDAO {

    private final FluentJdbc fluentJdbc;
    private final Entities<Town> towns;

    public TownPlayerDAO(FluentJdbc fluentJdbc, Entities<Town> towns) {
        this.fluentJdbc = fluentJdbc;
        this.towns = towns;
    }

    public void insert(TownPlayer townPlayer) {
        Query query = fluentJdbc.query();

        query
                .update("INSERT INTO town_players VALUES (?)")
                .params(
                        townPlayer.getUuid().toString()
                ).run();
    }

    public void drop(TownPlayer townPlayer) {
        Query query = fluentJdbc.query();

        query
                .update("DELETE FROM town_players WHERE player_uuid = ?")
                .params(
                        townPlayer.getUuid().toString()
                ).run();
    }

    public void insertTown(TownPlayer townPlayer) {
        Query query = fluentJdbc.query();

        query
                .update("INSERT INTO town_player_towns VALUES (?,?,?)")
                .params(
                        townPlayer.getUuid().toString(),
                        townPlayer.getTown().getUuid().toString(),
                        townPlayer.getTownRank().getName()
                ).run();
    }

    public void dropTown(TownPlayer townPlayer) {
        Query query = fluentJdbc.query();

        query
                .update("DELETE FROM town_player_towns WHERE player_uuid = ?")
                .params(
                        townPlayer.getUuid().toString()
                ).run();
    }

    public Town loadTown(TownPlayer townPlayer) {

        Mapper<Town> townMapper = rs -> towns.get(UUID.fromString(rs.getString("town_uuid")));

        Query query = fluentJdbc.query();

        Optional<Town> town = query
                .select("SELECT * FROM town_player_towns WHERE player_uuid = ?")
                .params(
                        townPlayer.getUuid().toString()
                ).firstResult(townMapper);

        return town.orElse(null);

    }

    public TownPlayer load(Player player) {

        Mapper<TownPlayer> townPlayerMapper = rs -> new TownPlayer(player);

        Query query = fluentJdbc.query();

        Optional<TownPlayer> townPlayer = query
                .select("SELECT * FROM town_players WHERE player_uuid = ?")
                .params(
                        player.getUniqueId().toString()
                ).firstResult(townPlayerMapper);

        if (townPlayer.isPresent()) {
            Town playerTown = loadTown(townPlayer.get());
            townPlayer.get().setTown(playerTown);
            return townPlayer.get();
        } else {
            return null;
        }

    }

}

