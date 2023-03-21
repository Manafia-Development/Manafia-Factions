package com.github.manafia.factions.cmd.drain;

import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.util.Cooldown;
import com.github.manafia.factions.util.TimeUtil;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;

/**
 * @author Saser
 */
public class CmdDrain extends FCommand {
    public CmdDrain() {
        this.aliases.addAll(Aliases.drain);

        this.optionalArgs.put("amount", "money");
        this.optionalArgs.put("role", "faction role");

        this.requirements = new CommandRequirements.Builder(Permission.DRAIN)
                .playerOnly()
                .memberOnly()
                .withAction(PermissableAction.DRAIN)
                .build();
    }


    @Override
    public void perform(CommandContext context) {
        if (!Conf.factionsDrainEnabled) {
            context.fPlayer.msg(TL.GENERIC_DISABLED, "Factions Drain");
            return;
        }
        if(Cooldown.isOnCooldown(context.player, "drainCooldown")) {
            long remaining = context.player.getMetadata("drainCooldown").get(0).asLong() - System.currentTimeMillis();
            int remainSec = (int) (remaining / 1000L);
            context.msg(CC.translate(TL.COMMAND_DRAIN_COOLDOWN.toString().replace("{seconds}", TimeUtil.formatSeconds(remainSec))));
            return;
        }

        int calculatedAmount = 0;
        boolean useRoleDrain = false;
        int amountToDrain = 0;
        Role roleToDrain = null;

        if (context.args.size() == 2) {
            amountToDrain = context.argAsInt(0);
            roleToDrain = Role.fromString(context.args.get(1));
            if (amountToDrain > 0) {
                useRoleDrain = true;
            }
        }


        for (FPlayer fPlayer : context.faction.getFPlayers()) {
            if (context.faction.getFPlayers().size() == 1) {
                context.fPlayer.msg(TL.COMMAND_DRAIN_NO_PLAYERS);
                return;
            }

            if (FPlayers.getInstance().getByPlayer(context.player).equals(fPlayer)) {
                continue; // skip the command executor
            }

            if (useRoleDrain) {
                if (fPlayer.getRole() == roleToDrain) {
                    double balance = FactionsPlugin.getInstance().getEcon().getBalance(fPlayer.getPlayer());
                    if (balance >= amountToDrain) {
                        FactionsPlugin.getInstance().getEcon().withdrawPlayer(fPlayer.getPlayer(), amountToDrain);
                        calculatedAmount += amountToDrain;
                    }
                }
            } else {
                amountToDrain = (int) FactionsPlugin.getInstance().getEcon().getBalance(fPlayer.getPlayer());
                calculatedAmount += amountToDrain;
                FactionsPlugin.getInstance().getEcon().withdrawPlayer(fPlayer.getPlayer(), amountToDrain);
            }
        }

        if(useRoleDrain) {
            context.msg(TL.COMMAND_DRAIN_ROLE_DRAINED_AMOUNT, roleToDrain, calculatedAmount);
        } else {
            context.msg(TL.COMMAND_DRAIN_RECIEVED_AMOUNT, calculatedAmount);
        }

        FactionsPlugin.getInstance().getEcon().depositPlayer(context.player, calculatedAmount);
        Cooldown.setCooldown(context.faction, "drainCooldown", Conf.factionDrainCooldown);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_DRAIN_DESCRIPTION;
    }
}
