package com.github.manafia.factions.cmd.econ;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.cmd.audit.FLogType;
import com.github.manafia.factions.iface.EconomyParticipator;
import com.github.manafia.factions.integration.Econ;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.ChatColor;


public class CmdMoneyDeposit extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMoneyDeposit() {
        super();
        this.aliases.addAll(Aliases.money_deposit);

        this.requiredArgs.add("amount");
        this.optionalArgs.put("faction", "yours");

        this.requirements = new CommandRequirements.Builder(Permission.MONEY_DEPOSIT)
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        double amount = context.argAsDouble(0, 0d);
        EconomyParticipator faction = context.argAsFaction(1, context.faction);

        if (amount <= 0) {
            return;
        }

        if (faction == null) {
            return;
        }
        boolean success = Econ.transferMoney(context.fPlayer, context.fPlayer, faction, amount);

        if (success && Conf.logMoneyTransactions) {
            Logger.printArgs(TL.COMMAND_MONEYDEPOSIT_DEPOSITED.toString(), Logger.PrefixType.DEFAULT, context.fPlayer.getName(), Econ.moneyString(amount), faction.describeTo(null));
            FactionsPlugin.instance.logFactionEvent(context.faction, FLogType.BANK_EDIT, context.fPlayer.getName(), ChatColor.GREEN + ChatColor.BOLD.toString() + "DEPOSITED", amount + "");

        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MONEYDEPOSIT_DESCRIPTION;
    }

}

