package com.github.manafia.factions.listeners;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginRegistry implements Listener {

    @EventHandler
    public void onJoinPreStart(PlayerJoinEvent e) {
        if (!FactionsPlugin.canPlayersJoin()) {
            e.getPlayer().kickPlayer(CC.translate(TL.PRE_JOIN_KICK_MESSAGE.toString()));
        }
    }
}
