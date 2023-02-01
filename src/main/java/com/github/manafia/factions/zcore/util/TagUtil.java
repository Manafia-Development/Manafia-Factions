package com.github.manafia.factions.zcore.util;


import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.Factions;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Relation;
import com.github.manafia.factions.util.MiscUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.github.manafia.factions.zcore.util.TagReplacer.TagType;

public class TagUtil {

    private static final int ARBITRARY_LIMIT = 25000;

    /**
     * Replaces all variables in a plain raw line for a faction
     *
     * @param faction for faction
     * @param line    raw line from config with variables to replace for
     * @return clean line
     */
    public static String parsePlain(Faction faction, String line) {
        for (TagReplacer tagReplacer : TagReplacer.getByType(TagType.FACTION)) {
            if (tagReplacer.contains(line)) {
                line = tagReplacer.replace(line, tagReplacer.getValue(faction, null));
            }
        }
        return line;
    }

    /**
     * Replaces all variables in a plain raw line for a player
     *
     * @param fplayer for player
     * @param line    raw line from config with variables to replace for
     * @return clean line
     */
    public static String parsePlain(FPlayer fplayer, String line) {
        for (TagReplacer tagReplacer : TagReplacer.getByType(TagType.PLAYER)) {
            if (tagReplacer.contains(line)) {
                String rep = tagReplacer.getValue(fplayer.getFaction(), fplayer);
                if (rep == null) {
                    rep = ""; // this should work, but it's not a good way to handle whatever is going wrong
                }
                line = tagReplacer.replace(line, rep);
            }
        }
        return line;
    }

    /**
     * Replaces all variables in a plain raw line for a faction, using relations from fplayer
     *
     * @param faction for faction
     * @param fplayer from player
     * @param line    raw line from config with variables to replace for
     * @return clean line
     */
    public static String parsePlain(Faction faction, FPlayer fplayer, String line) {
        for (TagReplacer tagReplacer : TagReplacer.getByType(TagType.PLAYER)) {
            if (tagReplacer.contains(line)) {
                String value = tagReplacer.getValue(faction, fplayer);
                if (value != null) {
                    line = tagReplacer.replace(line, value);
                } else {
                    return null; // minimal show, entire line to be ignored
                }
            }
        }
        return line;
    }

    /**
     * Scan a line and parse the fancy variable into a fancy list
     *
     * @param faction for faction (viewers faction)
     * @param fme     for player (viewer)
     * @param line    fancy message prefix
     * @return list of fancy msgs
     */
    public static List<FancyMessage> parseFancy(Faction faction, FPlayer fme, String line) {
        for (TagReplacer tagReplacer : TagReplacer.getByType(TagType.FANCY)) {
            if (tagReplacer.contains(line)) {
                String clean = line.replace(tagReplacer.getTag(), ""); // remove tag
                return getFancy(faction, fme, tagReplacer, clean);
            }
        }
        return null;
    }

    public static String parsePlaceholders(Player player, String line) {
        if (FactionsPlugin.getInstance().isClipPlaceholderAPIHooked() && player.isOnline()) {
            line = PlaceholderAPI.setPlaceholders(player, line);
        }

        if (FactionsPlugin.getInstance().isMVdWPlaceholderAPIHooked() && player.isOnline()) {
            line = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, line);
        }
        return line;
    }

    /**
     * Checks if a line has fancy variables
     *
     * @param line raw line from config with variables
     * @return if the line has fancy variables
     */
    public static boolean hasFancy(String line) {
        for (TagReplacer tagReplacer : TagReplacer.getByType(TagType.FANCY)) {
            if (tagReplacer.contains(line)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lets get fancy.
     *
     * @param target Faction to get relate from
     * @param fme    Player to relate to
     * @param prefix First part of the fancy message
     * @return list of fancy messages to send
     */
    protected static List<FancyMessage> getFancy(Faction target, FPlayer fme, TagReplacer type, String prefix) {
        List<FancyMessage> fancyMessages = new ArrayList<>();
        boolean minimal = FactionsPlugin.getInstance().getConfig().getBoolean("minimal-show", false);

        switch (type) {
            case ALLIES_LIST:
                FancyMessage currentAllies = FactionsPlugin.getInstance().txt.parseFancy(prefix);
                boolean firstAlly = true;
                for (Faction otherFaction : Factions.getInstance().getAllFactions()) {
                    if (otherFaction == target) {
                        continue;
                    }
                    String s = otherFaction.getTag(fme);
                    if (otherFaction.getRelationTo(target).isAlly()) {
                        currentAllies.then(firstAlly ? s : ", " + s);
                        currentAllies.tooltip(tipFaction(otherFaction)).color(fme != null ? fme.getColorTo(otherFaction) : Relation.NEUTRAL.getColor());
                        firstAlly = false;
                        if (currentAllies.toJSONString().length() > ARBITRARY_LIMIT) {
                            fancyMessages.add(currentAllies);
                            currentAllies = new FancyMessage("");
                        }
                    }
                }
                fancyMessages.add(currentAllies);
                return firstAlly && minimal ? null : fancyMessages; // we must return here and not outside the switch
            case ENEMIES_LIST:
                FancyMessage currentEnemies = FactionsPlugin.getInstance().txt.parseFancy(prefix);
                boolean firstEnemy = true;
                for (Faction otherFaction : Factions.getInstance().getAllFactions()) {
                    if (otherFaction == target) {
                        continue;
                    }
                    String s = otherFaction.getTag(fme);
                    if (otherFaction.getRelationTo(target).isEnemy()) {
                        currentEnemies.then(firstEnemy ? s : ", " + s);
                        currentEnemies.tooltip(tipFaction(otherFaction)).color(fme != null ? fme.getColorTo(otherFaction) : Relation.NEUTRAL.getColor());
                        firstEnemy = false;
                        if (currentEnemies.toJSONString().length() > ARBITRARY_LIMIT) {
                            fancyMessages.add(currentEnemies);
                            currentEnemies = new FancyMessage("");
                        }
                    }
                }
                fancyMessages.add(currentEnemies);
                return firstEnemy && minimal ? null : fancyMessages; // we must return here and not outside the switch
            case TRUCES_LIST:
                FancyMessage currentTruces = FactionsPlugin.getInstance().txt.parseFancy(prefix);
                boolean firstTruce = true;
                for (Faction otherFaction : Factions.getInstance().getAllFactions()) {
                    if (otherFaction == target) {
                        continue;
                    }
                    String s = otherFaction.getTag(fme);
                    if (otherFaction.getRelationTo(target).isTruce()) {
                        currentTruces.then(firstTruce ? s : ", " + s);
                        currentTruces.tooltip(tipFaction(otherFaction)).color(fme != null ? fme.getColorTo(otherFaction) : Relation.NEUTRAL.getColor());
                        firstTruce = false;
                        if (currentTruces.toJSONString().length() > ARBITRARY_LIMIT) {
                            fancyMessages.add(currentTruces);
                            currentTruces = new FancyMessage("");
                        }
                    }
                }
                fancyMessages.add(currentTruces);
                return firstTruce && minimal ? null : fancyMessages; // we must return here and not outside the switch
            case ONLINE_LIST:
                FancyMessage currentOnline = FactionsPlugin.getInstance().txt.parseFancy(prefix);
                boolean firstOnline = true;
                for (FPlayer p : MiscUtil.rankOrder(target.getFPlayersWhereOnline(true, fme))) {
                    if (fme != null && fme.getPlayer() != null && !fme.getPlayer().canSee(p.getPlayer())) {
                        continue; // skip
                    }
                    String name = p.getNameAndTitle();
                    currentOnline.then(firstOnline ? name : ", " + name);
                    currentOnline.tooltip(tipPlayer(p)).color(fme != null ? fme.getColorTo(p) : Relation.NEUTRAL.getColor());
                    firstOnline = false;
                    if (currentOnline.toJSONString().length() > ARBITRARY_LIMIT) {
                        fancyMessages.add(currentOnline);
                        currentOnline = new FancyMessage("");
                    }
                }
                fancyMessages.add(currentOnline);
                return firstOnline && minimal ? null : fancyMessages; // we must return here and not outside the switch
            case OFFLINE_LIST:
                FancyMessage currentOffline = FactionsPlugin.getInstance().txt.parseFancy(prefix);
                boolean firstOffline = true;
                for (FPlayer p : MiscUtil.rankOrder(target.getFPlayers())) {
                    String name = p.getNameAndTitle();
                    // Also make sure to add players that are online BUT can't be seen.
                    if (!p.isOnline() || (fme != null && fme.getPlayer() != null && !fme.getPlayer().canSee(p.getPlayer()))) {
                        currentOffline.then(firstOffline ? name : ", " + name);
                        currentOffline.tooltip(tipPlayer(p)).color(fme != null ? fme.getColorTo(p) : Relation.NEUTRAL.getColor());
                        firstOffline = false;
                        if (currentOffline.toJSONString().length() > ARBITRARY_LIMIT) {
                            fancyMessages.add(currentOffline);
                            currentOffline = new FancyMessage("");
                        }
                    }
                }
                fancyMessages.add(currentOffline);
                return firstOffline && minimal ? null : fancyMessages; // we must return here and not outside the switch
            case ALTS:
                FancyMessage alts = FactionsPlugin.getInstance().txt.parseFancy(prefix);
                boolean firstAlt = true;
                for (FPlayer p : target.getAltPlayers()) {
                    String name = p.getName();
                    ChatColor color;

                    if (p.isOnline()) {
                        color = ChatColor.GREEN;
                    } else {
                        color = ChatColor.RED;
                    }

                    alts.then(firstAlt ? name : ", " + name);
                    alts.tooltip(tipPlayer(p)).color(color);
                    firstAlt = false;
                    if (alts.toJSONString().length() > ARBITRARY_LIMIT) {
                        fancyMessages.add(alts);
                    }
                }
                fancyMessages.add(alts);
                return firstAlt && minimal ? null : fancyMessages;
            default:
                break;
        }
        return null;
    }

    /**
     * Parses tooltip variables from config <br> Supports variables for factions only (type 2)
     *
     * @param faction faction to tooltip for
     * @return list of tooltips for a fancy message
     */
    private static List<String> tipFaction(Faction faction) {
        List<String> lines = new ArrayList<>();
        for (String line : FactionsPlugin.getInstance().getConfig().getStringList("tooltips.list")) {
            lines.add(ChatColor.translateAlternateColorCodes('&', TagUtil.parsePlain(faction, line)));
        }
        return lines;
    }

    /**
     * Parses tooltip variables from config <br> Supports variables for players and factions (types 1 and 2)
     *
     * @param fplayer player to tooltip for
     * @return list of tooltips for a fancy message
     */
    private static List<String> tipPlayer(FPlayer fplayer) {
        List<String> lines = new ArrayList<>();
        for (String line : FactionsPlugin.getInstance().getConfig().getStringList("tooltips.show")) {
            lines.add(ChatColor.translateAlternateColorCodes('&', TagUtil.parsePlain(fplayer, line)));
        }
        return lines;
    }
}
