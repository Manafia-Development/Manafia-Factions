package com.massivecraft.factions.struct;

import com.cryptomorin.xseries.XMaterial;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.zcore.fperms.Permissable;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public enum Relation implements Permissable {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */


    MEMBER(4, TL.RELATION_MEMBER_SINGULAR.toString()),
    ALLY(3, TL.RELATION_ALLY_SINGULAR.toString()),
    TRUCE(2, TL.RELATION_TRUCE_SINGULAR.toString()),
    NEUTRAL(1, TL.RELATION_NEUTRAL_SINGULAR.toString()),
    ENEMY(0, TL.RELATION_ENEMY_SINGULAR.toString());

    public final int value;
    public final String nicename;

    Relation(final int value, final String nicename) {
        this.value = value;
        this.nicename = nicename;
    }

    public static Relation fromString(String s) {
        // not possible to add a switch statement to non-constant fields
        // sorry >.<
        if (s.equalsIgnoreCase(MEMBER.nicename))
            return MEMBER;
        else if (s.equalsIgnoreCase(ALLY.nicename))
            return ALLY;
        else if (s.equalsIgnoreCase(TRUCE.nicename))
            return TRUCE;
        else if (s.equalsIgnoreCase(ENEMY.nicename))
            return ENEMY;
        else
            return NEUTRAL; // If they somehow mess things up, go back to default behavior.
    }

    @Override
    public String toString() {
        return this.nicename;
    }

    public String getTranslation() {
        try {
            return TL.valueOf("RELATION_" + name() + "_SINGULAR").toString();
        } catch (IllegalArgumentException e) {
            return toString();
        }
    }

    public String getPluralTranslation() {
        for (TL t : TL.values())
            if (t.name().equalsIgnoreCase("RELATION_" + name() + "_PLURAL"))
                return t.toString();
        return toString();
    }

    public boolean isMember() {
        return this == MEMBER;
    }

    public boolean isAlly() {
        return this == ALLY;
    }

    public boolean isTruce() {
        return this == TRUCE;
    }

    public boolean isNeutral() {
        return this == NEUTRAL;
    }

    public boolean isEnemy() {
        return this == ENEMY;
    }

    public boolean isAtLeast(Relation relation) {
        return this.value >= relation.value;
    }

    public boolean isAtMost(Relation relation) {
        return this.value <= relation.value;
    }

    public ChatColor getColor() {

        switch (this) {
            case MEMBER:
                return Conf.colorMember;
            case ALLY:
                return Conf.colorAlly;
            case NEUTRAL:
                return Conf.colorNeutral;
            case TRUCE:
                return Conf.colorTruce;
            default:
                return Conf.colorEnemy;
        }
    }

    // return appropriate Conf setting for DenyBuild based on this relation and their online status
    public boolean confDenyBuild(boolean online) {
        if (isMember())
            return false;

        // cant add switch statement to this.
        // only switch I could do is for online by passing
        // the bool to an int with something like
        // int t = (online) ? 1 : 0, but it's pointless for 2 cases
        if (online)
            switch (this) {
                case ENEMY:
                    return Conf.territoryEnemyDenyBuild;
                case ALLY:
                    return Conf.territoryAllyDenyBuild;
                case TRUCE:
                    return Conf.territoryTruceDenyBuild;
                default:
                    return Conf.territoryDenyBuild;
            }
        else
            switch (this) {
                case ENEMY:
                    return Conf.territoryEnemyDenyBuildWhenOffline;
                case ALLY:
                    return Conf.territoryAllyDenyBuildWhenOffline;
                case TRUCE:
                    return Conf.territoryTruceDenyBuildWhenOffline;
                default:
                    return Conf.territoryDenyBuildWhenOffline;
            }
    }

    // return appropriate Conf setting for PainBuild based on this relation and their online status
    public boolean confPainBuild(boolean online) {
        if (isMember())
            return false;

        if (online)
            switch (this) {
                case ENEMY:
                    return Conf.territoryEnemyPainBuild;
                case ALLY:
                    return Conf.territoryAllyPainBuild;
                case TRUCE:
                    return Conf.territoryTrucePainBuild;
                default:
                    return Conf.territoryPainBuild;
            }
        else
            switch (this) {
                case ENEMY:
                    return Conf.territoryEnemyPainBuildWhenOffline;
                case ALLY:
                    return Conf.territoryAllyPainBuildWhenOffline;
                case TRUCE:
                    return Conf.territoryTrucePainBuildWhenOffline;
                default:
                    return Conf.territoryPainBuildWhenOffline;
            }
    }

    // return appropriate Conf setting for DenyUseage based on this relation
    public boolean confDenyUseage() {
        switch (this) {
            case MEMBER:
                return false;
            case ENEMY:
                return Conf.territoryEnemyDenyUsage;
            case ALLY:
                return Conf.territoryAllyDenyUsage;
            case TRUCE:
                return Conf.territoryTruceDenyUsage;
            default:
                return Conf.territoryDenyUsage;
        }
    }

    public double getRelationCost() {
        switch (this) {
            case ENEMY:
                return Conf.econCostEnemy;
            case ALLY:
                return Conf.econCostAlly;
            case TRUCE:
                return Conf.econCostTruce;
            default:
                return Conf.econCostNeutral;
        }
    }

    // Utility method to build items for F Perm GUI
    @Override
    public ItemStack buildItem() {
        final ConfigurationSection RELATION_CONFIG = FactionsPlugin.getInstance().getConfig().getConfigurationSection("fperm-gui.relation");

        String displayName = replacePlaceholders(RELATION_CONFIG.getString("placeholder-item.name", ""));
        List<String> lore = new ArrayList<>();

        Material material = XMaterial.matchXMaterial(RELATION_CONFIG.getString("materials." + name().toLowerCase())).get().parseMaterial();
        if (material == null)
            return null;

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        for (String loreLine : RELATION_CONFIG.getStringList("placeholder-item.lore"))
            lore.add(replacePlaceholders(loreLine));

        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    public String replacePlaceholders(String string) {
        string = ChatColor.translateAlternateColorCodes('&', string);

        String permissableName = nicename.substring(0, 1).toUpperCase() + nicename.substring(1);

        string = string.replace("{relation-color}", getColor().toString());
        string = string.replace("{relation}", permissableName);

        return string;
    }
}
