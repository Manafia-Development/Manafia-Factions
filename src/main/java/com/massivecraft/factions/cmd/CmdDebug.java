package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.Bukkit;

public class CmdDebug extends FCommand {
    public CmdDebug () {
        super();
        this.aliases.add("debug");
        this.requirements = new CommandRequirements.Builder(Permission.DEBUG).build();
    }

    @Override
    public void perform (CommandContext context) {
        System.out.print("----------Debug Info----------");
        System.out.print("-------Main-------");
        System.out.print("Server com.massivecraft.factions.Version: " + FactionsPlugin.getInstance().getServer().getVersion());
        System.out.print("Server Bukkit com.massivecraft.factions.Version: " + FactionsPlugin.getInstance().getServer().getBukkitVersion());
        System.out.print("UltimateFactions com.massivecraft.factions.Version: " + FactionsPlugin.getInstance().getDescription().getVersion());
        System.out.print("Is Beta com.massivecraft.factions.Version: " + (FactionsPlugin.getInstance().getDescription().getFullName().contains("BETA") ? "True" : "False"));
        System.out.print("Players Online: " + Bukkit.getOnlinePlayers().size());
        System.out.print("------Command------");
        System.out.print("Check/WeeWoo Commands: " + FCmdRoot.instance.checkEnabled);
        System.out.print("Mission Command: " + FCmdRoot.instance.missionsEnabled);
        System.out.print("Shop Command: " + FCmdRoot.instance.fShopEnabled);
        System.out.print("Inventory See Command: " + FCmdRoot.instance.invSeeEnabled);
        System.out.print("Points Command: " + FCmdRoot.instance.fPointsEnabled);
        System.out.print("Alts Command: " + FCmdRoot.instance.fAltsEnabled);
        System.out.print("Grace Command: " + FCmdRoot.instance.fGraceEnabled);
        System.out.print("Focus Command: " + FCmdRoot.instance.fFocusEnabled);
        System.out.print("Shield command: " + FCmdRoot.instance.ShieldEnabled);
        System.out.print("Fly Command: " + FCmdRoot.instance.fFlyEnabled);
        System.out.print("PayPal Commands: " + FCmdRoot.instance.fPayPalEnabled);
        System.out.print("Inspect Command: " + FCmdRoot.instance.coreProtectEnabled);
        System.out.print("Internal FTOP Command: " + FCmdRoot.instance.internalFTOPEnabled);
        System.out.print("----End Command----");
        System.out.print("-----End Main-----");
        System.out.print("--------End Debug Info--------");
        context.fPlayer.msg(TL.COMMAND_DEBUG_PRINTED);
    }

    @Override
    public TL getUsageTranslation () {
        return TL.COMMAND_DEBUG_DESCRIPTION;
    }
}
