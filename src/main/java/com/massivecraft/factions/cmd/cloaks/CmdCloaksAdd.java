package com.massivecraft.factions.cmd.cloaks;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cloaks.CloakType;
import com.massivecraft.factions.cmd.Aliases;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.util.CloakChunk;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public class CmdCloaksAdd extends FCommand {

    public CmdCloaksAdd() {
        super();
        this.aliases.addAll(Aliases.CloakAdd);

        this.requiredArgs.add("faction");
        this.requiredArgs.add("CloakType");
        //   this.requiredArgs.add("time");

        this.requirements = new CommandRequirements.Builder(Permission.CLOAK_ADD).playerOnly().memberOnly().build();
    }

    @Override
    public void perform(CommandContext context) {
        Faction fac = context.faction;
        Faction target = context.argAsFaction(0);
        Location location = context.player.getLocation();


        String cloakString = context.argAsString(1);
        CloakType cloakType = CloakType.getTypeFromString(cloakString);


        if (target == null) {
            context.sender.sendMessage(TL.COMMAND_CLOAKS_ADD_INVALID_FAC.format(context.argAsString(0)));
            return;
        }

        //   String timeLength = context.argAsString(3);
        //   TimeUtil timeUtil;
/*
        try {
            timeUtil = TimeUtil.parseString(timeLength);
        } catch (TimeUtil.TimeParseException e) {
            context.sender.sendMessage(TL.COMMAND_CLOAKS_ADD_INVALID_TIME.format(context.argAsString(3)));
            return;
        }

        long millis = timeUtil.toMilliseconds();
        long endTimeStamp = millis + System.currentTimeMillis();

        Cloak cloak = new Cloak(cloakType, endTimeStamp);
        target.addCloak(cloak);

 */


        CloakChunk cloakChunk = new CloakChunk(Objects.requireNonNull(location.getWorld()).getName(), location.getChunk().getX(), location.getChunk().getZ());
        if (fac.getCloakChunkCount() < fac.getAllowedCloakChunks()) {
            if (context.fPlayer.attemptClaim(fac, new FLocation(context.player.getLocation()), true)) {
                if (!fac.getCloakChunks().contains(cloakChunk)) {
                    fac.getCloakChunks().add(cloakChunk);
                    context.fPlayer.msg(TL.COMMAND_CLOAKS_CLAIM_SUCCESSFUL);
                } else {
                    context.fPlayer.msg(TL.COMMAND_CLOAKS_ALREADY_CHUNK);
                }
            }
        } else {
            context.fPlayer.msg(TL.COMMAND_CLOAKS_PAST_LIMIT, fac.getAllowedCloakChunks());
        }
/*
        context.sender.sendMessage(TL.COMMAND_CLOAKS_ADD_SUCCESSFUL.format(context.argAsString(1), context.argAsString(0),
                timeUtil.toString()));

 */

        Bukkit.broadcastMessage(String.valueOf(TL.COMMAND_CLOAKS_STARTED_ANNOUNCE));
    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_CLOAKS_ADD_DESCRIPTION;
    }
}
