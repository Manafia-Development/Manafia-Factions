package com.massivecraft.factions.util.wait;

import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.entity.Player;

/**
 * @author droppinganvil
 */

public class WaitTask {
    private final TL msg;
    //Using player as to not have to convert every event
    private final Player player;
    private final WaitedTask origin;
    private Integer wait;

    public WaitTask(Integer wait, TL message, Player player, WaitedTask waitedTask) {
        this.wait = wait;
        this.msg = message;
        this.player = player;
        this.origin = waitedTask;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer i) {
        wait = i;
    }

    public TL getMessage() {
        return msg;
    }

    public Player getPlayer() {
        return player;
    }

    public void success() {
        origin.handleSuccess(player);
    }

    public void fail() {
        origin.handleFailure(player);
    }
}
