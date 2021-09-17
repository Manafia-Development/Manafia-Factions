package com.massivecraft.factions.cmd.raids;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.Util;
import com.massivecraft.factions.cmd.*;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.security.PublicKey;
import java.util.List;

public class CmdRaid extends FCommand {

    public CmdRaid() {
        super();
        this.aliases.addAll(Aliases.raid);
        this.requiredArgs.add("faction");
        this.requirements = new CommandRequirements.Builder(Permission.RAID)
                .memberOnly()
                .withRole(Role.LEADER)
                .build();

    }

    @Override
    public void perform(CommandContext context) {


        Faction target = context.argAsFaction(0);
        String me = context.sender.getName();
        Faction meFac = context.fPlayer.getFaction();
        if (target == null) {
            context.sender.sendMessage(TL.COMMAND_RAID_INVALID_FAC.format(context.argAsString(0)));
            return;
        }

        context.faction = target;


        if (!FCmdRoot.instance.raidEnabled) {
            context.msg(TL.GENERIC_DISABLED, "Faction Raids");
            boolean isEnabled = false;
            return;
        }

        if (context.fPlayer.getRole() != Role.LEADER && !context.fPlayer.isAdminBypassing()) {
            context.msg(TL.COMMAND_RAID_HELP_1, "set a raid!");
            context.msg(TL.COMMAND_RAID_HELP_2);
            return;
        }
    
        if(context.faction.isProtected()) {
            context.sender.sendMessage(TL.COMMAND_RAID_DENIED.format(target, me));
        }    
        if (context.faction.isPeaceful()) {
            context.sender.sendMessage(TL.COMMAND_RAID_DENIED.format(target));
            return;
        }
        if (context.faction.isShieldRunning()) {
            context.sender.sendMessage(TL.COMMAND_RAID_SHIELD_RUNNING.format(target, me));
            return;
        }
        if (context.faction.getAllClaims().isEmpty()) {
            context.sender.sendMessage(TL.COMMAND_RAID_NOCLAIMS.format(context.argAsString(0)));
            return;
        }
        if (context.faction.getTag().equals(context.fPlayer.getTag())) {
            context.sender.sendMessage(String.valueOf(TL.COMMAND_RAID_DENIED_YOURSELF));
            return;
        }

        if (context.faction.isNormal()) {
            Bukkit.broadcastMessage(TL.COMMAND_RAID_STARTED_BROADCAST.format(me, target.getTag()));
        //    boolean RaidStarted = true;

        if (FactionsPlugin.getInstance().getFileManager().getRaids().getConfig().getBoolean("Mechanics.Spawner-Break", false)) {
            
           // boolean raidSpawners = true;

            //TODO add a timer!

        }
            return;
        }
    }


        @Override
        public TL getUsageTranslation() {
            return TL.COMMAND_RAID_DESCRIPTION;
        }
    }