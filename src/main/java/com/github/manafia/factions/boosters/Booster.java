package com.github.manafia.factions.boosters;

public class Booster {

    private final BoosterType boosterType;
    private final long endTimeStamp;
    private final double multiplier;

    public Booster(BoosterType boosterType, long endTimeStamp, double multiplier) {
        this.boosterType = boosterType;
        this.endTimeStamp = endTimeStamp;
        this.multiplier = multiplier;
    }

    public BoosterType getBoosterType() {
        return boosterType;
    }


    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public double getMultiplier() {
        return multiplier;
    }

}