package com.shepherdjerred.sttowns.objects.trackers;

import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.AbstractEntity;

import java.util.*;

public class Entities<V extends AbstractEntity> {

    protected final Map<UUID, V> entityMap = new HashMap<>();

    public void remove(UUID uuid) {
        entityMap.remove(uuid);
    }

    public void remove(V entity) {
        entityMap.remove(entity.getUuid());
    }

    public void remove(String name) {
        entityMap.remove(get(name).getUuid());
    }

    public void add(V entity) throws TrackerException {

        if (entity.getName() == null) {
            throw new TrackerException("Name cannot be null");
        }

        if (entity.getName() != null && contains(entity.getName())) {
            throw new TrackerException("That name is already taken in this object tracker");
        }

        if (entity.getName() != null && entity.getName().length() < 3 || entity.getName().length() > 16) {
            throw new TrackerException("Name must be between 3-16 characters");
        }

        entityMap.put(entity.getUuid(), entity);
    }

    public void addAll(List<V> entities) throws TrackerException {
        for (V entity : entities) {
            add(entity);
        }
    }

    public V get(V entity) {
        return entityMap.get(entity.getUuid());
    }

    public V get(UUID uuid) {
        return entityMap.get(uuid);
    }

    public V get(String name) {
        for (V entity : entityMap.values()) {
            if (entity.getName().equalsIgnoreCase(name)) {
                return entity;
            }
        }
        return null;
    }

    public List<V> getAsList() {
        return new ArrayList<>(entityMap.values());
    }

    public boolean contains(String name) {
        if (get(name) == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean contains(UUID uuid) {
        return entityMap.containsKey(uuid);
    }

    public boolean contains(V entity) {
        return entityMap.containsValue(entity);
    }

}
