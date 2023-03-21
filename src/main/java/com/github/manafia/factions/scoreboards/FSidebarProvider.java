package com.github.manafia.factions.scoreboards;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.tag.Tag;
import com.github.manafia.factions.zcore.util.TL;

import java.util.List;

public abstract class FSidebarProvider {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public abstract String getTitle(FPlayer fplayer);

    public abstract List<String> getLines(FPlayer fplayer);

    public String replaceTags(FPlayer fPlayer, String s) {
        s = Tag.parsePlaceholders(fPlayer.getPlayer(), s);

        return qualityAssure(Tag.parsePlain(fPlayer, s));
    }

    public String replaceTags(Faction faction, FPlayer fPlayer, String s) {
        // Run through Placeholder API first
        s = Tag.parsePlaceholders(fPlayer.getPlayer(), s);

        return qualityAssure(Tag.parsePlain(faction, fPlayer, s));
    }

    private String qualityAssure(String line) {
        if (line.contains("{notFrozen}") || line.contains("{notPermanent}")) {
            return "n/a"; // we dont support support these error variables in scoreboards
        }
        if (line.contains("{ig}")) {
            // since you can't really fit a whole "Faction Home: world, x, y, z" on one line
            // we assume it's broken up into two lines, so returning our tl will suffice.
            return TL.COMMAND_SHOW_NOHOME.toString();
        }
        return FactionsPlugin.getInstance().txt.parse(line); // finally add color :)
    }
}