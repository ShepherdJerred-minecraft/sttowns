package com.shepherdjerred.sttowns.objects;

import com.shepherdjerred.sttowns.objects.ranks.GroupAction;
import com.shepherdjerred.sttowns.objects.ranks.Rank;

import java.util.*;

public abstract class AbstractGroup<T extends GroupAction> extends AbstractEntity {

    protected final Map<UUID, Rank<T>> ranks = new HashMap<>();

    public AbstractGroup(UUID uuid, String name) {
        super(uuid, name);
    }

    public Rank<T> getRank(UUID uuid) {
        return ranks.get(uuid);
    }

    public void setRank(UUID uuid, Rank<T> rank) {
        ranks.put(uuid, rank);
    }

    public void remove(UUID uuid) {
        ranks.remove(uuid);
    }

    public List<UUID> getMembers() {
        return new ArrayList<>(ranks.keySet());
    }

    @Override
    public String toString() {
        return "AbstractGroup{" +
                "ranks=" + ranks +
                "} " + super.toString();
    }

}
