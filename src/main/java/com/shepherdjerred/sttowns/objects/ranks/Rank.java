package com.shepherdjerred.sttowns.objects.ranks;

import com.shepherdjerred.sttowns.objects.AbstractEntity;

import java.util.*;

public class Rank<T extends GroupAction> extends AbstractEntity {

    private final Map<T, Boolean> permissions = new HashMap<>();
    private final List<RankFlag> flags = new ArrayList<>();

    public Rank(UUID uuid, String name) {
        super(uuid, name);
    }

    public Rank(UUID uuid, String name, Map<T, Boolean> permissions, List<RankFlag> flags) {
        super(uuid, name);
        this.permissions.putAll(permissions);
        this.flags.addAll(flags);
    }

    public boolean hasPermission(T permission) {
        return permissions.getOrDefault(permission, false);
    }

    public void setPermission(T permission, boolean value) {
        permissions.put(permission, value);
    }

    public boolean hasFlag(RankFlag flag) {
        return flags.contains(flag);
    }

    @Override
    public String toString() {
        return "Rank{" +
                "permissions=" + permissions +
                ", flags=" + flags +
                "} " + super.toString();
    }

    public enum RankFlag {
        IMMUTABLE_PERMISSIONS, MEMBER, OWNER, PERMANENT, UNKICKABLE, REQUIRED
    }

}
