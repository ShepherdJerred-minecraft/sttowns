package com.shepherdjerred.sttowns.objects;

import com.shepherdjerred.sttowns.objects.invites.TownInvite;
import com.shepherdjerred.sttowns.objects.ranks.Rank;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.*;

public class TownPlayer extends AbstractEntity {

    private Town town;
    private final Map<UUID, TownInvite> townInvites = new HashMap<>();
    private WeakReference<Plot> lastPlot = null;

    public TownPlayer(Player player) {
        super(player.getUniqueId(), player.getName());
        town = null;
    }

    public TownPlayer(UUID uuid, String name, Town town) {
        super(uuid, name);
        this.town = town;
    }

    public Plot getLastPlot() {
        return lastPlot.get();
    }

    public void setLastPlotTown(Plot plot) {
        this.lastPlot = new WeakReference<>(plot);
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public boolean hasTown() {
        return town != null;
    }

    public Rank<Town.TownAction> getTownRank() {
        return town.getRank(uuid);
    }

    public boolean hasTownInvite(Town town) {
        return townInvites.containsKey(town.getUuid());
    }

    public void addInvite(TownInvite townInvite) {
        townInvites.put(townInvite.getInvitedTo().getUuid(), townInvite);
    }

    public TownInvite getTownInvite(Town town) {
        return townInvites.get(town.getUuid());
    }

    public List<TownInvite> getInvitesAsList() {
        return new ArrayList<>(townInvites.values());
    }

    public void removeInvite(Town town) {
        townInvites.remove(town.getUuid());
    }

    public Map<UUID, TownInvite> getTownInvites() {
        return townInvites;
    }

    public boolean hasPermission(Town.TownAction townAction) {
        return getTownRank().hasPermission(townAction);
    }

    public boolean hasFlag(Rank.RankFlag rankFlag) {
        return getTownRank().hasFlag(rankFlag);
    }

    @Override
    public String toString() {
        return "TownPlayer{" +
                "towns=" + town +
                ", townInvites=" + townInvites +
                "} " + super.toString();
    }
}
