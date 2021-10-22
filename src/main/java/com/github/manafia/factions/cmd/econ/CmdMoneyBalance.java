package com.github.manafia.factions.cmd.econ;

import com.github.manafia.factions.Faction;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.integration.Econ;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdMoneyBalance extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMoneyBalance () {
        super();
        this.aliases.addAll(Aliases.money_balance);

        //this.requiredArgs.add("");
        this.optionalArgs.put("faction", "yours");

        this.setHelpShort(TL.COMMAND_MONEYBALANCE_SHORT.toString());

        this.requirements = new CommandRequirements.Builder(Permission.MONEY_BALANCE).build();
    }

    @Override
    public void perform (CommandContext context) {
        Faction faction = context.faction;
        if (context.argIsSet(0))
            faction = context.argAsFaction(0);
        if (faction == null)
            return;
        if (faction != context.faction && !Permission.MONEY_BALANCE_ANY.has(context.sender, true))
            return;
        Econ.sendBalanceInfo(context.sender, faction);
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_MONEYBALANCE_DESCRIPTION;
    }

}