package com.github.manafia.factions.cmd.claim;

import com.github.manafia.factions.Board;
import com.github.manafia.factions.FLocation;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.Aliases;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.cmd.audit.FLogType;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.CC;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;

public class CmdClaimAt extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdClaimAt() {
        super();
        this.aliases.addAll(Aliases.claim_at);

        this.requiredArgs.add("world");
        this.requiredArgs.add("x");
        this.requiredArgs.add("z");

        this.requirements = new CommandRequirements.Builder(Permission.CLAIMAT)
                .playerOnly()
                .memberOnly()
                .withAction(PermissableAction.TERRITORY)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        int x = context.argAsInt(1);
        int z = context.argAsInt(2);
        FLocation location = new FLocation(context.argAsString(0), x, z);

        Faction at = Board.getInstance().getFactionAt(new FLocation(context.fPlayer.getPlayer().getLocation()));

        if (FactionsPlugin.cachedRadiusClaim && context.fPlayer.attemptClaim(context.fPlayer.getFaction(), context.player.getLocation(), true)) {
            context.fPlayer.getFaction().getFPlayersWhereOnline(true).forEach(f -> f.msg(TL.CLAIM_CLAIMED, context.fPlayer.describeTo(f, true), context.fPlayer.getFaction().describeTo(f), at.describeTo(f)));
            FactionsPlugin.instance.logFactionEvent(context.fPlayer.getFaction(), FLogType.CHUNK_CLAIMS, context.fPlayer.getName(), CC.GreenB + "CLAIMED", "1", (new FLocation(context.fPlayer.getPlayer().getLocation())).formatXAndZ(","));
            showMap(context);
            return;
        }
        context.fPlayer.attemptClaim(context.faction, location, true);
        FactionsPlugin.instance.logFactionEvent(context.fPlayer.getFaction(), FLogType.CHUNK_CLAIMS, context.fPlayer.getName(), CC.GreenB + "CLAIMED", "1", (location).formatXAndZ(","));
        showMap(context);
    }

    public void showMap(CommandContext context) {
        context.sendFancyMessage(Board.getInstance().getMap(context.fPlayer, new FLocation(context.fPlayer), context.player.getLocation().getYaw()));
    }


    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
