package com.shepherdjerred.sttowns.objects;


import com.shepherdjerred.sttowns.objects.ranks.GroupAction;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Nation extends AbstractSociety {

    private final Map<Nation, Relationship> relationships = new HashMap<>();

    public Nation(UUID uuid, String name, String motd) {
        super(uuid, name, motd, false);
    }

    public Nation(UUID uuid, String name, String motd, boolean open) {
        super(uuid, name, motd, open);
    }

    public Map<Nation, Relationship> getRelationships() {
        return relationships;
    }

    public enum Relationship {
        NEUTRAL, ALLY, ENEMY, WAR
    }

    public enum NationAction implements GroupAction {
        DESTROY
    }

}
