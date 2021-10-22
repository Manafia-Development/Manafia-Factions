package com.github.manafia.factions.missions;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdMissions extends FCommand {

    /**
     * @author Driftay
     */

    public CmdMissions() {
        this.aliases.add("missions");
        this.aliases.add("mission");
        this.aliases.add("objectives");
        this.aliases.add("objective");

        this.requirements = new CommandRequirements.Builder(Permission.MISSIONS)
                .memberOnly()
                .playerOnly()
                .build();
    }


    @Override
    public void perform(CommandContext context) {
        final MissionGUI missionsGUI = new MissionGUI(FactionsPlugin.getInstance(), context.fPlayer);
        missionsGUI.build();
        context.player.openInventory(missionsGUI.getInventory());
    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MISSION_DESCRIPTION;
    }
}
