package com.github.manafia.factions.cmd.econ;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.cmd.audit.FLogType;
import com.github.manafia.factions.integration.Econ;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.util.Logger;
import com.github.manafia.factions.zcore.fperms.Access;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;


public class CmdMoneyWithdraw extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMoneyWithdraw() {
        this.aliases.addAll(Aliases.money_withdraw);

        this.requiredArgs.add("amount");
        this.optionalArgs.put("faction", "yours");

        this.requirements = new CommandRequirements.Builder(Permission.MONEY_F2P)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        double amount = context.argAsDouble(0, 0d);

        if (amount <= 0) {
            return;
        }

        Faction faction = context.argAsFaction(1, context.faction);
        if (faction == null) {
            return;
        }

        Access access = context.faction.getAccess(context.fPlayer, PermissableAction.WITHDRAW);
        if (context.fPlayer.getRole() != Role.LEADER) {
            if (access == Access.DENY) {
                context.msg(TL.GENERIC_NOPERMISSION, "withdraw", "withdraw money from the bank");
                return;
            }
        }
        boolean success = Econ.transferMoney(context.fPlayer, faction, context.fPlayer, amount);

        if (success && Conf.logMoneyTransactions) {
            Logger.printArgs(TL.COMMAND_MONEYWITHDRAW_WITHDRAW.toString(), Logger.PrefixType.WARNING, context.fPlayer.getName(), Econ.moneyString(amount), faction.describeTo(null));
            FactionsPlugin.instance.logFactionEvent(faction, FLogType.BANK_EDIT, context.fPlayer.getName(), CC.RedB + "WITHDREW", amount + "");
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MONEYWITHDRAW_DESCRIPTION;
    }
}
