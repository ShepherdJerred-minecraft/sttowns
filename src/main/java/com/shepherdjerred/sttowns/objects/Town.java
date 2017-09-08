package com.shepherdjerred.sttowns.objects;

import com.shepherdjerred.sttowns.objects.ranks.GroupAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Town extends AbstractSociety<Town.TownAction> {

    private final Map<Plot.PlotType, Integer> remainingPlots = new HashMap<>();
    private Nation nation;

    public Town(UUID uuid, String name, String motd) {
        super(uuid, name, motd, false);
        nation = null;
    }

    public Town(UUID uuid, String name, String motd, boolean open, Nation nation) {
        super(uuid, name, motd, open);
        this.nation = nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public Nation getNation() {
        return nation;
    }

    public int getRemainingPlots(Plot.PlotType plotType) {
        return remainingPlots.getOrDefault(plotType, 0);
    }

    public boolean canClaimMore(Plot.PlotType plotType) {
        return getRemainingPlots(plotType) > 0;
    }

    public Map<Plot.PlotType, Integer> getRemainingPlots() {
        return remainingPlots;
    }

    public void setRemainingPlots(Plot.PlotType plotType, int amount) {
        remainingPlots.put(plotType, amount);
    }

    public List<Player> getOnlinePlayers() {
        List<Player> onlinePlayers = new ArrayList<>();
        for (UUID member : getMembers()) {
            if (Bukkit.getOfflinePlayer(member).isOnline()) {
                onlinePlayers.add(Bukkit.getPlayer(member));
            }
        }
        return onlinePlayers;
    }

    @Override
    public String toString() {
        return "Town{" +
                "remainingPlots=" + remainingPlots +
                ", nation=" + nation +
                "} " + super.toString();
    }

    public enum TownAction implements GroupAction {
        BYPASS_PLOTS, CLAIM, DESTROY, INVITE, JOIN_NATION, KICK, SET_MOTD, SET_NAME, SET_OPEN, SET_RANK, UNCLAIM
    }

}
