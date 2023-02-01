package com.github.manafia.factions.scoreboards.sidebar;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.scoreboards.FSidebarProvider;

import java.util.List;
import java.util.ListIterator;

public class FInfoSidebar extends FSidebarProvider {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    private final Faction faction;

    public FInfoSidebar(Faction faction) {
        this.faction = faction;
    }

    @Override
    public String getTitle(FPlayer fplayer) {
        return faction.getRelationTo(fplayer).getColor() + faction.getTag();
    }

    @Override
    public List<String> getLines(FPlayer fplayer) {
        List<String> lines = FactionsPlugin.getInstance().getConfig().getStringList("scoreboard.finfo");

        ListIterator<String> it = lines.listIterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next == null) {
                it.remove();
                continue;
            }
            String replaced = replaceTags(faction, fplayer, next);
            if (replaced == null) {
                it.remove();
            } else {
                it.set(replaced);
            }
        }
        return lines;
    }
}