package com.massivecraft.factions.cloaks;

public class Cloak {

   private final CloakType cloakType;
   private final long endTimeStamp;
   private final double multiplier;

}

public Cloak(CloakType cloakType, long endTimeStamp) {
   this.cloakType = cloakType;
   this.endTimeStamp = endTimeStamp;

   public CloakType getCloakType() {
      return cloakType;
   }

   public long endTimeStamp() {
      return endTimeStamp;
   }

}
