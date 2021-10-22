package com.github.manafia.factions.shop;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.CommandContext;
import com.github.manafia.factions.cmd.CommandRequirements;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.zcore.util.TL;

public class CmdShop extends FCommand {

    /**
     * @author Driftay
     */

    public CmdShop() {
        super();
        this.aliases.add("shop");
        this.requirements = new CommandRequirements.Builder(Permission.SHOP)
                .memberOnly()
                .playerOnly()
                .build();
    }


    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("F-Shop.Enabled"))
            return;
        new ShopGUIFrame(context.faction).buildGUI(context.fPlayer);
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
