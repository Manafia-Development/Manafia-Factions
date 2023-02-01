package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.ChatColor;

/**
 * Factions - Developed by Driftay.
 * All rights reserved 2020.
 * Creation Date: 6/20/2020
 */
public class CmdSetPower extends FCommand {

    public CmdSetPower() {
        this.aliases.addAll(Aliases.setPower);
        this.requiredArgs.add("player");
        this.requiredArgs.add("number");

        this.requirements = new CommandRequirements.Builder(Permission.SETPOWER)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        FPlayer targetPlayer = context.argAsFPlayer(0);
        int value = context.argAsInt(1, -1);
        if (value < 0) {
            context.sender.sendMessage(ChatColor.RED + "Number must be greater than 0.");
            return;
        }

        if (targetPlayer == null) {
            context.sender.sendMessage(ChatColor.RED + "Player is not online!");
            return;
        }

        if (value > targetPlayer.getPowerMaxRounded()) {
            context.sender.sendMessage(ChatColor.RED + "Number must be less than the players max-power.");
            return;
        }

        if (targetPlayer.isAlt() && !FactionsPlugin.getInstance().getConfig().getBoolean("f-alts.Have-Power")) {
            context.sender.sendMessage(ChatColor.RED + "The target cannot be an alt account.");
            return;
        }

        targetPlayer.setPowerRounded(value);
        context.sender.sendMessage(TL.COMMAND_SETPOWER_SUCCESS.format(targetPlayer.getName(), value));
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_SETPOWER_DESCRIPTION;
    }

}
