package com.massivecraft.factions.cloaks;

public enum CloakType {

   NORMAL,
   SHIELD,
   SPECIAL;

}

public static CloakType getTypeFromString(String name) {
   CloakType = null;

   for (CloakType cloakType: values()) {
      if (cloakType.name().equalsIgnoreCase(name)) {
         type = cloakType;
         break;
      }
      return type;
   }
}
