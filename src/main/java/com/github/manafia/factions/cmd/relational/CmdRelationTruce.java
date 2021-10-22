package com.github.manafia.factions.cmd.relational;

import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.struct.Relation;

public class CmdRelationTruce extends FRelationCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdRelationTruce () {
        aliases.addAll(Aliases.relation_truce);
        targetRelation = Relation.TRUCE;
    }
}
