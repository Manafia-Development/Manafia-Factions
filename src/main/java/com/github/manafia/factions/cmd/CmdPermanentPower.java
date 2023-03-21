package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdPermanentPower extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdPermanentPower() {
        super();
        this.aliases.addAll(Aliases.permanent_power);
        this.requiredArgs.add("faction");
        this.requiredArgs.add("power");

        this.requirements = new CommandRequirements.Builder(Permission.SET_PERMANENTPOWER)
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        Faction targetFaction = context.argAsFaction(0);
        if (targetFaction == null) {
            return;
        }

        Integer targetPower = context.argAsInt(1);

        targetFaction.setPermanentPower(targetPower);

        String change = TL.COMMAND_PERMANENTPOWER_REVOKE.toString();
        if (targetFaction.hasPermanentPower()) {
            change = TL.COMMAND_PERMANENTPOWER_GRANT.toString();
        }

        // Inform sender
        context.msg(TL.COMMAND_PERMANENTPOWER_SUCCESS, change, targetFaction.describeTo(context.fPlayer));

        // Inform all other players
        for (FPlayer fplayer : targetFaction.getFPlayersWhereOnline(true)) {
            if (fplayer == context.fPlayer) {
                continue;
            }
            String blame = (context.fPlayer == null ? TL.GENERIC_SERVERADMIN.toString() : context.fPlayer.describeTo(fplayer, true));
            fplayer.msg(TL.COMMAND_PERMANENTPOWER_FACTION, blame, change);
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_PERMANENTPOWER_DESCRIPTION;
    }
}
