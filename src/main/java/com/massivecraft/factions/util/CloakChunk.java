package com.massivecraft.factions.util;

import com.massivecraft.factions.FLocation;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.Objects;

public class CloakChunk {

    private String worldName;
    private int x, z;
    private long timeClaimed;

    public CloakChunk(String worldName, int x, int z) {
        this.worldName = worldName;
        this.x = x;
        this.z = z;
    }

    public CloakChunk(String worldName, FLocation floc) {
        this.worldName = worldName;
        this.x = floc.getChunk().getX();
        this.z = floc.getChunk().getZ();
    }

    public CloakChunk(FLocation floc) {
        this.worldName = floc.getWorldName();
        this.x = floc.getChunk().getX();
        this.z = floc.getChunk().getZ();
    }


    public CloakChunk(FLocation fLoc, Long timeClaimed) {
        this.worldName = fLoc.getWorldName();
        this.timeClaimed = timeClaimed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloakChunk cloakChunk = (CloakChunk) o;
        return x == cloakChunk.x &&
                z == cloakChunk.z &&
                worldName.equals(cloakChunk.worldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(worldName, x, z);
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public long getTimeClaimed() {
        return timeClaimed;
    }


    public Chunk getChunk() {
        return Bukkit.getWorld(worldName).getChunkAt(x, z);
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }
}
