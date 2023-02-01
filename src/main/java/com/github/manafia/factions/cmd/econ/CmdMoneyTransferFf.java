package com.github.manafia.factions.cmd.econ;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.iface.EconomyParticipator;
import com.github.manafia.factions.integration.Econ;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.entity.Player;


public class CmdMoneyTransferFf extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMoneyTransferFf() {
        this.aliases.addAll(Aliases.money_transfer_Ff);

        this.requiredArgs.add("amount");
        this.requiredArgs.add("faction");
        this.requiredArgs.add("faction");

        this.requirements = new CommandRequirements.Builder(Permission.MONEY_F2F).build();
    }

    @Override
    public void perform(CommandContext context) {
        double amount = context.argAsDouble(0, 0d);

        if (amount <= 0) {
            return;
        }


        EconomyParticipator from = context.argAsFaction(1);
        if (from == null) {
            return;
        }
        EconomyParticipator to = context.argAsFaction(2);
        if (to == null) {
            return;
        }

        boolean success = Econ.transferMoney(context.fPlayer, from, to, amount);

        if (success && Conf.logMoneyTransactions) {
            String name = context.sender instanceof Player ? context.fPlayer.getName() : context.sender.getName();
            Logger.printArgs(TL.COMMAND_MONEYTRANSFERFF_TRANSFER.toString(), Logger.PrefixType.DEFAULT, name, Econ.moneyString(amount), from.describeTo(null), to.describeTo(null));
        }
    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MONEYTRANSFERFF_DESCRIPTION;
    }
}