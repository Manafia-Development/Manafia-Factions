package com.massivecraft.factions.missions;

public class Mission {

    private final String name;
    private final String type;
    /**
     * @author Driftay
     */

    private long progress;

    public Mission(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public long getProgress() {
        return progress;
    }

    public void incrementProgress() {
        ++progress;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
