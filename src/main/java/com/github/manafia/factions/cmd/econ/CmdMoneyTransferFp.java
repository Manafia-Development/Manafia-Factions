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


public class CmdMoneyTransferFp extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMoneyTransferFp() {
        this.aliases.addAll(Aliases.money_transfer_Fp);

        this.requiredArgs.add("amount");
        this.requiredArgs.add("faction");
        this.requiredArgs.add("player");

        this.requirements = new CommandRequirements.Builder(Permission.MONEY_F2P).build();
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
        EconomyParticipator to = context.argAsBestFPlayerMatch(2);
        if (to == null) {
            return;
        }

        boolean success = Econ.transferMoney(context.fPlayer, from, to, amount);

        if (success && Conf.logMoneyTransactions) {
            Logger.printArgs(TL.COMMAND_MONEYTRANSFERFP_TRANSFER.toString(), Logger.PrefixType.DEFAULT, context.fPlayer.getName(), Econ.moneyString(amount), from.describeTo(null), to.describeTo(null));
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MONEYTRANSFERFP_DESCRIPTION;
    }
}

