package com.github.manafia.factions.cmd.claim;

/**
 * Created by FactionsUUID Team
 */

import com.github.manafia.factions.Board;
import com.github.manafia.factions.Conf;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.fperms.Access;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CmdClaimFill extends FCommand {

    public CmdClaimFill () {

        // Aliases
        this.aliases.addAll(Aliases.claim_claimFill);

        // Args
        this.optionalArgs.put("limit", String.valueOf(Conf.maxFillClaimCount));
        this.optionalArgs.put("faction", "you");

        this.requirements = new CommandRequirements.Builder(Permission.CLAIM_FILL)
                .playerOnly()
                .build();
    }

    @Override
    public void perform (CommandContext context) {
        // Args
        final int limit = context.argAsInt(0, Conf.maxFillClaimCount);

        if (limit > Conf.maxFillClaimCount) {
            context.msg(TL.COMMAND_CLAIMFILL_ABOVEMAX, Conf.maxFillClaimCount);
            return;
        }

        final Faction forFaction = context.argAsFaction(2, context.faction);
        Location location = context.player.getLocation();
        FLocation loc = new FLocation(location);

        Faction currentFaction = Board.getInstance().getFactionAt(loc);

        if (currentFaction.equals(forFaction)) {
            context.msg(TL.CLAIM_ALREADYOWN, forFaction.describeTo(context.fPlayer, true));
            return;
        }

        if (!currentFaction.isWilderness()) {
            context.msg(TL.COMMAND_CLAIMFILL_ALREADYCLAIMED);
            return;
        }

        if (!context.fPlayer.isAdminBypassing() && forFaction.getAccess(context.fPlayer, PermissableAction.TERRITORY) != Access.ALLOW) {
            context.msg(TL.CLAIM_CANTCLAIM, forFaction.describeTo(context.fPlayer));
            return;
        }

        final double distance = Conf.maxFillClaimDistance;
        long startX = loc.getX();
        long startZ = loc.getZ();

        Set<FLocation> toClaim = new HashSet<>();
        Queue<FLocation> queue = new LinkedList<>();
        FLocation currentHead;
        queue.add(loc);
        toClaim.add(loc);
        while (!queue.isEmpty() && toClaim.size() <= limit) {
            currentHead = queue.poll();

            if (Math.abs(currentHead.getX() - startX) > distance || Math.abs(currentHead.getZ() - startZ) > distance) {
                context.msg(TL.COMMAND_CLAIMFILL_TOOFAR, distance);
                return;
            }

            addIf(toClaim, queue, currentHead.getRelative(0, 1));
            addIf(toClaim, queue, currentHead.getRelative(0, -1));
            addIf(toClaim, queue, currentHead.getRelative(1, 0));
            addIf(toClaim, queue, currentHead.getRelative(-1, 0));
        }

        if (toClaim.size() > limit) {
            context.msg(TL.COMMAND_CLAIMFILL_PASTLIMIT);
            return;
        }

        if (toClaim.size() > context.faction.getPowerRounded() - context.faction.getLandRounded()) {
            context.msg(TL.COMMAND_CLAIMFILL_NOTENOUGHLANDLEFT, forFaction.describeTo(context.fPlayer), toClaim.size());
            return;
        }

        final int limFail = Conf.radiusClaimFailureLimit;
        int fails = 0;
        for (FLocation currentLocation : toClaim) {
            if (!context.fPlayer.attemptClaim(forFaction, currentLocation, true))
                fails++;
            if (fails >= limFail) {
                context.msg(TL.COMMAND_CLAIMFILL_TOOMUCHFAIL, fails);
                return;
            }
        }
    }

    private void addIf (Set<FLocation> toClaim, Queue<FLocation> queue, FLocation examine) {
        if (Board.getInstance().getFactionAt(examine).isWilderness() && !toClaim.contains(examine)) {
            toClaim.add(examine);
            queue.add(examine);
        }
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_CLAIMFILL_DESCRIPTION;
    }
}
