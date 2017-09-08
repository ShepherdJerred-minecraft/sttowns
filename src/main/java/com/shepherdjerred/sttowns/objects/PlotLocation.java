package com.shepherdjerred.sttowns.objects;

import org.bukkit.Chunk;

import java.util.UUID;

public class PlotLocation {

    private final int x;
    private final int z;
    private final UUID world;

    public PlotLocation(int x, int z, UUID world) {
        this.x = x;
        this.z = z;
        this.world = world;
    }

    public PlotLocation(Chunk chunk) {
        x = chunk.getX();
        z = chunk.getZ();
        world = chunk.getWorld().getUID();
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public UUID getWorld() {
        return world;
    }

    @Override
    public String toString() {
        return "PlotLocation{" +
                "x=" + x +
                ", z=" + z +
                ", world=" + world +
                '}';
    }
}
