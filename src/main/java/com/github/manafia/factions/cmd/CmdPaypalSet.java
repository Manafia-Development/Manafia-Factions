package com.github.manafia.factions.cmd;

import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.zcore.util.TL;

public class CmdPaypalSet extends FCommand {

    /**
     * @author Driftay
     */

    public CmdPaypalSet() {
        this.aliases.addAll(Aliases.paypal_set);

        this.optionalArgs.put("faction", "yours");

        this.requiredArgs.add("email");

        this.requirements = new CommandRequirements.Builder(Permission.PAYPALSET)
                .playerOnly()
                .memberOnly()
                .build();

    }

    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("fpaypal.Enabled")) {
            context.fPlayer.msg(TL.GENERIC_DISABLED, "Faction Paypals");
            return;
        }
        if (context.fPlayer.getRole() != Role.LEADER && !context.fPlayer.isAdminBypassing()) {
            //TODO: Create f perm for this
            context.msg(TL.GENERIC_NOPERMISSION, "set your factions PayPal!");
            return;
        }

        switch (context.args.size()) {
            case 1:
                if (isEmail(context.argAsString(0))) {
                    context.fPlayer.getFaction().paypalSet(context.argAsString(0));
                    context.msg(TL.COMMAND_PAYPALSET_SUCCESSFUL, context.argAsString(0));
                } else
                    context.msg(TL.COMMAND_PAYPALSET_NOTEMAIL, context.argAsString(0));
            case 2:
                if (context.fPlayer.isAdminBypassing()) {
                    Faction faction = context.argAsFaction(1);
                    if (faction != null) {
                        if (isEmail(context.argAsString(0))) {
                            context.fPlayer.getFaction().paypalSet(context.argAsString(0));
                            context.msg(TL.COMMAND_PAYPALSET_ADMIN_SUCCESSFUL, faction.getTag(), context.argAsString(0));
                        } else
                            context.msg(TL.COMMAND_PAYPALSET_ADMIN_FAILED, context.argAsString(0));
                    }
                } else
                    context.msg(TL.GENERIC_NOPERMISSION, "set another factions paypal!");
            default:
                context.msg(FactionsPlugin.getInstance().cmdBase.cmdPaypalSet.getUsageTemplate(context));
        }
    }

    private boolean isEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_PAYPALSET_DESCRIPTION;
    }
}

