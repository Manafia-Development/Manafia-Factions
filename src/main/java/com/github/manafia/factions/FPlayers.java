package com.github.manafia.factions;

import com.github.manafia.factions.zcore.persist.json.JSONFPlayers;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;

public abstract class FPlayers {
    protected static FPlayers instance = getFPlayersImpl();

    public static FPlayers getInstance() {
        return instance;
    }

    private static FPlayers getFPlayersImpl() {
        if (Conf.backEnd == Conf.Backend.JSON) {
            return new JSONFPlayers();
        }
        return null;
    }

    public abstract void clean();

    public abstract Collection<FPlayer> getOnlinePlayers();

    public abstract FPlayer getByPlayer(Player player);

    public abstract Collection<FPlayer> getAllFPlayers();

    public abstract void forceSave();

    public abstract void forceSave(boolean sync);

    public abstract FPlayer getByOfflinePlayer(OfflinePlayer player);

    public abstract FPlayer getById(String string);

    public abstract void load();
}
