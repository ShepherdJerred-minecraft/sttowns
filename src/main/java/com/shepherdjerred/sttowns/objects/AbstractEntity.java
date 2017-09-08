package com.shepherdjerred.sttowns.objects;

import java.util.UUID;

public abstract class AbstractEntity {

    protected final UUID uuid;
    protected String name;

    public AbstractEntity(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }
}
