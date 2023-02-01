package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.frame.fupgrades.FUpgradeFrame;
import com.github.manafia.factions.zcore.util.TL;

public class CmdUpgrades extends FCommand {

    /**
     * @author Illyria Team
     */

    public CmdUpgrades() {
        super();
        this.aliases.addAll(Aliases.upgrades);

        this.requirements = new CommandRequirements.Builder(Permission.UPGRADES)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getFileManager().getUpgrades().getConfig().getBoolean("fupgrades.Enabled")) {
            context.fPlayer.msg(TL.COMMAND_UPGRADES_DISABLED);
            return;
        }
        new FUpgradeFrame(context.faction).buildGUI(context.fPlayer);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_UPGRADES_DESCRIPTION;
    }

}
