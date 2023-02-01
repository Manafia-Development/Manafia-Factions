package com.github.manafia.factions.cmd.econ;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.zcore.util.TL;

public class CmdMoney extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdMoneyBalance cmdMoneyBalance = new CmdMoneyBalance();
    public CmdMoneyDeposit cmdMoneyDeposit = new CmdMoneyDeposit();
    public CmdMoneyWithdraw cmdMoneyWithdraw = new CmdMoneyWithdraw();
    public CmdMoneyTransferFf cmdMoneyTransferFf = new CmdMoneyTransferFf();
    public CmdMoneyTransferFp cmdMoneyTransferFp = new CmdMoneyTransferFp();
    public CmdMoneyTransferPf cmdMoneyTransferPf = new CmdMoneyTransferPf();

    public CmdMoney() {
        super();
        this.aliases.addAll(Aliases.money_money);

        //this.requiredArgs.add("");
        //this.optionalArgs.put("","")

        this.helpLong.add(FactionsPlugin.getInstance().txt.parseTags(TL.COMMAND_MONEY_LONG.toString()));

        this.addSubCommand(this.cmdMoneyBalance);
        this.addSubCommand(this.cmdMoneyDeposit);
        this.addSubCommand(this.cmdMoneyWithdraw);
        this.addSubCommand(this.cmdMoneyTransferFf);
        this.addSubCommand(this.cmdMoneyTransferFp);
        this.addSubCommand(this.cmdMoneyTransferPf);
    }

    @Override
    public void perform(CommandContext context) {
        if (!Conf.econEnabled || !Conf.bankEnabled) {
            context.msg(TL.ECON_OFF, "economy option is enabled, please set 'econEnabled' to true in conf.json");
            return;
        }
        context.commandChain.add(this);
        FactionsPlugin.getInstance().cmdAutoHelp.execute(context);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MONEY_DESCRIPTION;
    }

}
