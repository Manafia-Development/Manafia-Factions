package com.massivecraft.factions.boosters;

public enum BoosterType {

    POWER,
    EXP,
    MOBDROPS,
    MOBSPAWN;

    public static BoosterType getTypeFromString(String name) {
        BoosterType type = null;

        for (BoosterType boosterType : values()) {
            if (boosterType.name().equalsIgnoreCase(name)) {
                type = boosterType;
                break;
            }
        }

        return type;
    }

}