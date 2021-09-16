package com.massivecraft.factions.lunar;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.object.StaffModule;
import com.lunarclient.bukkitapi.serverrule.LunarClientAPIServerRule;
import com.massivecraft.factions.FactionsPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class LunarClientUserListener implements Listener {

    private final LunarMods client;


    @EventHandler
    public void onPlayerRegister(PlayerJoinEvent event) {
        if (client.getPacketModSettings() != null) {
            LunarClientAPI.getInstance().sendPacket(event.getPlayer(), client.getPacketModSettings());
        }

        LunarClientAPIServerRule.sendServerRule(event.getPlayer());
    }


    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        long delay = 10;


        if (FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getBoolean("Staff.XRAY", true)) {
            LunarClientAPI.getInstance().setStaffModuleState(player, StaffModule.XRAY, true);

        }
    }
}












