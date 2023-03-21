package com.github.manafia.factions.cmd.roles;

import com.github.manafia.factions.cmd.Aliases;

public class CmdDemote extends FPromoteCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdDemote() {
        aliases.addAll(Aliases.roles_demote);
        this.relative = -1;
    }
}
