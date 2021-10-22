package com.github.manafia.factions.cmd.tnt;

import com.github.manafia.factions.Faction;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.ChatColor;

public class CmdSetTnt extends FCommand {

    public CmdSetTnt () {
        this.aliases.addAll(Aliases.setTnt);
        this.requiredArgs.add("faction");
        this.requiredArgs.add("amount");

        this.requirements = new CommandRequirements.Builder(Permission.SET_TNT).build();
    }

    @Override
    public void perform (CommandContext context) {
        Faction targetFac = context.argAsFaction(0);
        int value = context.argAsInt(1, -1);

        if (value < 0) {
            context.sender.sendMessage(ChatColor.RED + "Number must be greater than 0.");
            return;
        }

        if (targetFac == null) {
            context.sender.sendMessage(ChatColor.RED + "Faction does not exist!");
            return;
        }

        if (targetFac.isSystemFaction()) {
            context.sender.sendMessage(ChatColor.RED + "You cannot set the tnt of System Factions!");
            return;
        }

        if (value > targetFac.getTntBankLimit()) {
            context.sender.sendMessage(ChatColor.RED + "Number must be less than the factions tnt bank limit.");
            return;
        }

        targetFac.setTnt(value);
        context.sender.sendMessage(TL.COMMAND_SETTNT_SUCCESS.format(targetFac.getTag(), value));


    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_SETTNT_DESCRIPTION;
    }
}