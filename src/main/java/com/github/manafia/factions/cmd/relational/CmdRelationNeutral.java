package com.github.manafia.factions.cmd.relational;

import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.struct.Relation;

public class CmdRelationNeutral extends FRelationCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdRelationNeutral() {
        aliases.addAll(Aliases.relation_neutral);
        targetRelation = Relation.NEUTRAL;
    }
}
