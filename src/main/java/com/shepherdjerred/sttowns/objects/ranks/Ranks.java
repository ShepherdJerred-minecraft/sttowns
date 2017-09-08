package com.shepherdjerred.sttowns.objects.ranks;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.Nation;
import com.shepherdjerred.sttowns.objects.Plot;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import org.apache.commons.lang3.EnumUtils;

import java.io.IOException;
import java.util.*;

public class Ranks {

    private final Entities<Rank<Plot.PlotAction>> plotRanks = new Entities<>();
    private final Entities<Rank<Town.TownAction>> townRanks = new Entities<>();
    private final Entities<Rank<Nation.NationAction>> nationRanks = new Entities<>();

    public void loadPlots() throws TrackerException {

    }

    public void loadTowns(String jsonString) throws TrackerException, IOException {

        JsonValue json = Json.parse(jsonString);
        JsonObject ranks = json.asObject().get("ranks").asObject();

        for (JsonObject.Member rank : ranks) {
            String name = rank.getName();
            JsonArray flags = rank.getValue().asObject().get("flags").asArray();
            JsonObject permissions = rank.getValue().asObject().get("permissions").asObject();

            List<Rank.RankFlag> flagList = new ArrayList<>();
            Map<Town.TownAction, Boolean> permissionMap = new HashMap<>();

            for (JsonValue flag : flags) {
                if (!EnumUtils.isValidEnum(Rank.RankFlag.class, flag.asString())) {
                    System.out.println("INVALID FLAG: " + flag.asString());
                    continue;
                }
                flagList.add(Rank.RankFlag.valueOf(flag.asString()));
            }

            for (JsonObject.Member permission : permissions) {
                if (!EnumUtils.isValidEnum(Town.TownAction.class, permission.getName())) {
                    System.out.println("INVALID PERMISSION: " + permission.getName());
                    continue;
                }
                permissionMap.put(Town.TownAction.valueOf(permission.getName()), permission.getValue().asBoolean());
            }

            Rank<Town.TownAction> finishedRank = new Rank<>(UUID.randomUUID(), name, permissionMap, flagList);
            townRanks.add(finishedRank);
            System.out.println(finishedRank);
        }

    }

    public void loadNations() {

    }

    public Entities<Rank<Plot.PlotAction>> getPlotRanks() {
        return plotRanks;
    }

    public Entities<Rank<Town.TownAction>> getTownRanks() {
        return townRanks;
    }

    public Entities<Rank<Nation.NationAction>> getNationRanks() {
        return nationRanks;
    }
}
