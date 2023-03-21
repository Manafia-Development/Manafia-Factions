package com.github.manafia.factions.cmd.relational;

import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.struct.Relation;

public class CmdRelationEnemy extends FRelationCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdRelationEnemy() {
        aliases.addAll(Aliases.relation_enemy);
        targetRelation = Relation.ENEMY;
    }
}
