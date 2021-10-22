package com.github.manafia.factions.cmd.relational;

import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.struct.Relation;

public class CmdRelationAlly extends FRelationCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdRelationAlly () {
        aliases.addAll(Aliases.relation_ally);
        targetRelation = Relation.ALLY;
    }
}
