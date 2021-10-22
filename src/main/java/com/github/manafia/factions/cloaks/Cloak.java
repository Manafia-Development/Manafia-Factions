package com.github.manafia.factions.cloaks;

public class Cloak {

    private final CloakType cloakType;
    private final long endTimeStamp;


    public Cloak(CloakType cloakType, long endTimeStamp) {
        this.cloakType = cloakType;
        this.endTimeStamp = endTimeStamp;
    }


    public CloakType getCloakType() {
        return cloakType;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }


}

