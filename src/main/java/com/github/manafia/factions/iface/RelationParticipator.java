package com.github.manafia.factions.iface;

import com.github.manafia.factions.struct.Relation;
import org.bukkit.ChatColor;

public interface RelationParticipator {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    String describeTo(RelationParticipator that);

    String describeTo(RelationParticipator that, boolean ucfirst);

    Relation getRelationTo(RelationParticipator that);

    Relation getRelationTo(RelationParticipator that, boolean ignorePeaceful);

    ChatColor getColorTo(RelationParticipator to);
}
