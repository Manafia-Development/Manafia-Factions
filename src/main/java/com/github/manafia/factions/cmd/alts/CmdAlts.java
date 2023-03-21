package com.github.manafia.factions.cmd.alts;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdAlts extends FCommand {

    /**
     * @author Driftay
     */

    public CmdInviteAlt cmdInviteAlt = new CmdInviteAlt();
    public CmdAltsList cmdAltsList = new CmdAltsList();
    public CmdKickAlt cmdKickAlt = new CmdKickAlt();


    public CmdAlts() {
        super();
        this.aliases.addAll(Aliases.alts_alts);
        this.addSubCommand(this.cmdInviteAlt);
        this.addSubCommand(this.cmdAltsList);
        this.addSubCommand(this.cmdKickAlt);

        this.requirements = new CommandRequirements.Builder(Permission.ALTS)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("f-alts.Enabled", false)) {
            context.msg(TL.GENERIC_DISABLED, "Faction Alts");
            return;
        }

        context.commandChain.add(this);
        FactionsPlugin.getInstance().cmdAutoHelp.execute(context);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_ALTS_DESCRIPTION;
    }

}
