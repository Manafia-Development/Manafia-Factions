package com.github.manafia.factions.lunar;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketModSettings;
import com.lunarclient.bukkitapi.nethandler.client.obj.ModSettings;
import com.github.manafia.factions.FactionsPlugin;
import lombok.Getter;

import java.util.HashMap;

public class LunarMods {


    @Getter
    private static LunarMods instance;

    @Getter
    private LCPacketModSettings packetModSettings = null;


    private void mods() {

        ModSettings.ModSetting disabled = new ModSettings.ModSetting(false, new HashMap<>());

        if (FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getBoolean("Mods.Zoom", false)) {
            new ModSettings()
                    .addModSetting("Zoom", disabled);
            return;

        }

        if (FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getBoolean("Mods.Coordinates", false)) {
            new ModSettings()
                    .addModSetting("Coordinates", disabled);
            return;
        }

        packetModSettings = new LCPacketModSettings();

    }












}






