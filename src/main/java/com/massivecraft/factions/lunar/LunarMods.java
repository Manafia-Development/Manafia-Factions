package com.massivecraft.factions.lunar;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketModSettings;
import com.lunarclient.bukkitapi.nethandler.client.obj.ModSettings;
import com.massivecraft.factions.FactionsPlugin;
import org.dynmap.jetty.util.preventers.AbstractLeakPreventer;

import java.util.HashMap;

public class LunarMods {


    public static void lunarmods() {

        ModSettings.ModSetting disabled = new ModSettings.ModSetting(false, new HashMap<>());



        if(FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getBoolean("Mods.Coordinates", false))



            //Add packet send here
            new ModSettings()
                    .addModSetting("Coordinates", disabled);


        if(FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getBoolean("Mods.Zoom", false))


            //Add packet send here
            new ModSettings()
                    .addModSetting("Zoom", disabled);




        private void loadDisabledMods() {
            // If we have the disabled mods key, and its a list we want to set mod settings.
            if (getConfig().contains("force-disabled-mods") && getConfig().isList("force-disabled-mods")) {
                ModSettings modSettings = new ModSettings();
                // Go through all the items in the list, and disable each mod.
                for (String modId : getConfig().getStringList("force-disabled-mods")) {
                    modSettings.addModSetting(modId, new ModSettings.ModSetting(false, new HashMap<>()));
                }
                packetModSettings = new LCPacketModSettings(modSettings);
            }
        }
    }






/*
        sendPacket(event.getPlayer(), new LCPacketModSettings(
                new ModSettings()
                        .addModSetting("Coordinates", disabled)
                        .addModSetting("textHotKey", disabled)
        ));

 */


    }
}
