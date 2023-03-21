package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.zcore.util.TL;

public class CmdSpawnerLock extends FCommand {

    /**
     * @author Illyria Team
     */

    public CmdSpawnerLock() {
        super();
        this.aliases.addAll(Aliases.spawnerlock);

        this.requirements = new CommandRequirements.Builder(Permission.LOCKSPAWNERS)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        Conf.spawnerLock = !Conf.spawnerLock;
        context.msg(TL.COMMAND_SPAWNER_LOCK_TOGGLED, Conf.spawnerLock ? CC.translate("&aEnabled") : CC.translate("&4Disabled"));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_SPAWNER_LOCK_DESCRIPTION;
    }
}
