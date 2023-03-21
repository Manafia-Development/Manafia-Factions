package com.github.manafia.factions.cmd.roles;

import com.github.manafia.factions.cmd.Aliases;

public class CmdPromote extends FPromoteCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdPromote() {
        aliases.addAll(Aliases.roles_promote);
        this.relative = 1;
    }
}
