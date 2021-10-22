package com.github.manafia.factions.lunar;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.object.StaffModule;
import com.lunarclient.bukkitapi.serverrule.LunarClientAPIServerRule;
import com.github.manafia.factions.FactionsPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class LunarClientUserListener implements Listener {

  private final LunarMods client;

  @EventHandler
  private void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    Faction faction = fPlayer.getFaction();
    Player1 player1 = fplayer.getPlayer();

    if (LunarClientAPI.getInstance().isRunningLunarClient(player)) {
      LunarClientAPI.getInstance().registerPlayer(player);

      if (FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getBoolean("Factions.Waypoints.Faction-Home.Enabled", true)) {
        if (player1.hasFaction()) {
          LCWaypoint waypoint = new LCWaypoint("Faction Home", faction.getHome(), Color.(FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getString("Factions.Waypoints.Factions-Home.Color")).asRGB(), true);
          LunarClientAPI.getInstance().sendWaypoint(player, waypoint);

        }

      }
      if (fPlayer.isAdminBypassing()) {

        if (FactionsPlugin.getInstance().getFileManager().getLunar().getConfig().getBoolean("Staff.XRAY", true)) {
          LunarClientAPI.getInstance().setStaffModuleState(player, StaffModule.XRAY, true);

        }
      }
    }
  }

  @EventHandler
  public void onUnregister(PlayerQuitEvent event) {
    lunarClientAPI.unregisterPlayer(event.getPlayer(), true);
  }

}
//        Bukkit.getPluginManager().registerEvents(new LunarClientUserListener(this), this);
