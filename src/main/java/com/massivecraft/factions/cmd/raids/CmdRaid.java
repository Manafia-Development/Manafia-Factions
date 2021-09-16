package com.massivecraft.factions.cmd.raids;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.cmd.Aliases;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.CommandRequirements;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import com.massivecraft.factions.zcore.util.TL;

public class CmdRaid extends FCommand {

    public CmdRaid() {
        super();
        this.aliases.addAll(Aliases.raid);
        this.requiredArgs.add("faction name");
        this.requirements = new CommandRequirements.Builder(Permission.RAID)
                .memberOnly()
                .withRole(Role.LEADER)
                .build();

    }

    @Override
    public void perform(CommandContext context) {

        Faction them = context.argAsFaction(0);
        if (them == null) return;


        if (!FactionsPlugin.getInstance().getFileManager().getRaids().getConfig().getBoolean("Enabled", false)) {
            context.msg(TL.GENERIC_DISABLED, "Faction Raids");
            boolean isEnabled = false;
            return;
        }

        if(context.fPlayer.getRole() !=Role.LEADER && !context.fPlayer.isAdminBypassing()) {
            context.msg(TL.COMMAND_RAID_HELP_1, "set a raid!");
            context.msg(TL.COMMAND_RAID_HELP_2);
            return;
        }

        if(context.faction.isProtected()) {
            context.msg(TL.COMMAND_RAID_DENIED, context.faction);
        }
        if(context.faction.isPeaceful()) {
            context.msg(TL.COMMAND_RAID_DENIED, context.faction);
        }
        if(context.faction.isShieldRunning()) {
            context.msg(TL.COMMAND_RAID_SHIELD_RUNNING, context.faction);
        }
        if(context.faction.getAllClaims().isEmpty()) {
            context.msg(TL.COMMAND_RAID_NOCLAIMS);
        }

        if(context.faction.isNormal()) {
            if(context.fPlayer.getFaction().getRelationTo(context.fPlayer) == Relation.ENEMY) {
                Faction faction = context.argAsFaction(0, context.faction);
                faction.msg(TL.COMMAND_RAID_STARTED_ANNOUNCE);
            }

        }


        }
    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_RAID_DESCRIPTION;
    }
}





