package com.github.manafia.factions.cmd.chest;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;

public class CmdChest extends FCommand {

    /**
     * @author Illyria Team
     */

    public CmdChest() {
        this.aliases.addAll(Aliases.chest);

        this.requirements = new CommandRequirements.Builder(Permission.CHEST)
                .playerOnly()
                .memberOnly()
                .withAction(PermissableAction.CHEST)
                .build();
    }

    @Override
    public void perform(CommandContext context) {


        if (!FactionsPlugin.getInstance().getConfig().getBoolean("fchest.Enabled")) {
            context.msg(TL.GENERIC_DISABLED, "Faction Chests");
            return;
        }
        // This permission check is way too explicit but it's clean
        context.fPlayer.setInFactionsChest(true);
        context.player.openInventory(context.faction.getChestInventory());
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_VAULT_DESCRIPTION;
    }
}
