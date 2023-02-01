package com.github.manafia.factions.cmd.claim;

import com.github.manafia.factions.*;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.cmd.audit.FLogType;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.util.SpiralTask;
import com.github.manafia.factions.zcore.fperms.Access;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;


public class CmdClaim extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdClaim() {
        super();
        this.aliases.addAll(Aliases.claim_claim);

        //this.requiredArgs.add("");
        this.optionalArgs.put("radius", "1");
        this.optionalArgs.put("faction", "your");

        this.requirements = new CommandRequirements.Builder(Permission.CLAIM)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {

        // Read and validate input
        int radius = context.argAsInt(0, 1); // Default to 1
        final Faction forFaction = context.argAsFaction(1, context.faction); // Default to own

        if (!context.fPlayer.isAdminBypassing()) {
            if (!(context.fPlayer.getFaction().equals(forFaction) && context.fPlayer.getRole() == Role.LEADER)) {
                if (forFaction.getAccess(context.fPlayer, PermissableAction.TERRITORY) != Access.ALLOW) {
                    context.msg(TL.COMMAND_CLAIM_DENIED);
                    return;
                }
            }
        }


        if (radius < 1) {
            context.msg(TL.COMMAND_CLAIM_INVALIDRADIUS);
            return;
        }

        if(radius > Conf.claimRadiusLimit && Conf.claimRadiusLimit != -1 && !context.fPlayer.isAdminBypassing()) {
            context.msg(TL.COMMAND_CLAIM_RADIUSOVER, Conf.claimRadiusLimit);
            return;
        }

        Faction at = Board.getInstance().getFactionAt(new FLocation(context.fPlayer.getPlayer().getLocation()));

        if (radius < 2) {
            if (forFaction.isSystemFaction() && context.fPlayer.attemptClaim(forFaction, context.player.getLocation(), false) && FactionsPlugin.cachedRadiusClaim) {
                context.fPlayer.msg(TL.CLAIM_CLAIMED, context.fPlayer.describeTo(context.fPlayer, true), forFaction.describeTo(context.fPlayer), at.describeTo(forFaction));
                return;
            }
            if (FactionsPlugin.cachedRadiusClaim && context.fPlayer.attemptClaim(forFaction, context.player.getLocation(), false)) {
                context.fPlayer.getFaction().getFPlayersWhereOnline(true).forEach(f -> f.msg(TL.CLAIM_CLAIMED, context.fPlayer.describeTo(f, true), context.fPlayer.getFaction().describeTo(f), at.describeTo(f)));
                FactionsPlugin.instance.logFactionEvent(forFaction, FLogType.CHUNK_CLAIMS, context.fPlayer.getName(), CC.GreenB + "CLAIMED", "1", (new FLocation(context.fPlayer.getPlayer().getLocation())).formatXAndZ(","));
                return;
            }
            context.fPlayer.attemptClaim(forFaction, context.player.getLocation(), true);
            FactionsPlugin.instance.logFactionEvent(forFaction, FLogType.CHUNK_CLAIMS, context.fPlayer.getName(), CC.GreenB + "CLAIMED", "1", (new FLocation(context.fPlayer.getPlayer().getLocation())).formatXAndZ(","));
        } else {
            // radius claim
            if (!Permission.CLAIM_RADIUS.has(context.sender, true)) {
                return;
            }
            new SpiralTask(new FLocation(context.player), radius) {
                private final int limit = Conf.radiusClaimFailureLimit - 1;
                private int failCount = 0;
                private int successfulClaims = 0;

                @Override
                public boolean work() {
                    boolean success = context.fPlayer.attemptClaim(forFaction, this.currentLocation(), true);
                    if (success) {
                        failCount = 0;
                        successfulClaims++;
                        FactionsPlugin.instance.logFactionEvent(forFaction, FLogType.CHUNK_CLAIMS, context.fPlayer.getName(), CC.GreenB + "CLAIMED", "1", (new FLocation(context.fPlayer.getPlayer().getLocation())).formatXAndZ(","));
                    } else if (failCount++ >= limit) {
                        this.stop();
                        return false;
                    }
                    return true;
                }

                public void finish() {
                    if (FactionsPlugin.cachedRadiusClaim) {
                        if (successfulClaims > 0) {
                            if (forFaction.isWarZone() || forFaction.isSafeZone()) {
                                context.fPlayer.msg(TL.CLAIM_RADIUS_CLAIM, context.fPlayer.describeTo(context.fPlayer, true), String.valueOf(successfulClaims), context.fPlayer.getPlayer().getLocation().getChunk().getX(), context.fPlayer.getPlayer().getLocation().getChunk().getZ());
                            } else {
                                context.fPlayer.getFaction().getFPlayersWhereOnline(true).forEach(f -> f.msg(TL.CLAIM_RADIUS_CLAIM, context.fPlayer.describeTo(f, true), String.valueOf(successfulClaims), context.fPlayer.getPlayer().getLocation().getChunk().getX(), context.fPlayer.getPlayer().getLocation().getChunk().getZ()));
                            }
                            stop();
                        }
                    }
                }
            };
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_CLAIM_DESCRIPTION;
    }

}
