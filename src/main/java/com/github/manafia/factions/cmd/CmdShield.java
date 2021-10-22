package com.github.manafia.factions.cmd;

import com.github.manafia.factions.shield.ShieldFrame;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.zcore.util.TL;

public class CmdShield extends FCommand {

    public CmdShield() {
        super();
        this.aliases.addAll(Aliases.shield);

        this.requirements = new CommandRequirements.Builder(Permission.SHIELD)
                .playerOnly()
                .memberOnly()
                .withRole(Role.LEADER)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (context.faction.isShieldSelected()) {
            context.player.sendMessage(TL.SHIELD_INFO.format(context.faction.getShieldStart(), context.faction.getShieldEnd()));
        } else {
            new ShieldFrame(context.faction).open(context.player);
        }
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
