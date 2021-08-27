package com.massivecraft.factions.cmd;

import com.massivecraft.factions.shield.ShieldFrame;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Role;
import com.massivecraft.factions.zcore.util.TL;

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
