package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.Faction;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CmdAHome extends FCommand {

    /**
     * @author FactionsUUID Team - Modified By CmdrKittens
     */

    public CmdAHome() {
        super();
        this.aliases.addAll(Aliases.ahome);

        this.requiredArgs.add("player");

        this.requirements = new CommandRequirements.Builder(Permission.AHOME).noDisableOnLock().build();
    }


    @Override
    public void perform(CommandContext context) {
        FPlayer target = context.argAsBestFPlayerMatch(0);
        if (target == null) {
            context.msg(TL.GENERIC_NOPLAYERMATCH, context.argAsString(0));
            return;
        }

        if (target.isOnline()) {
            Faction faction = target.getFaction();
            if (faction.hasHome()) {
                target.getPlayer().teleport(faction.getHome(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                context.msg(TL.COMMAND_AHOME_SUCCESS, target.getName());
                target.msg(TL.COMMAND_AHOME_TARGET);
            } else {
                context.msg(TL.COMMAND_AHOME_NOHOME, target.getName());
            }
        } else {
            context.msg(TL.COMMAND_AHOME_OFFLINE, target.getName());
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_AHOME_DESCRIPTION;
    }
}