package com.github.manafia.factions.cmd;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.util.ItemBuilder;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CmdGetVault extends FCommand {

    /**
     * @author Illyria Team
     */

    public CmdGetVault() {
        super();
        this.aliases.addAll(Aliases.getvault);

        this.requirements = new CommandRequirements.Builder(Permission.GETVAULT)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("fvault.Enabled")) {
            context.fPlayer.msg(TL.GENERIC_DISABLED, "Faction Vaults");
            return;
        }

        Location vaultLocation = context.faction.getVault();
        ItemStack vault = new ItemBuilder(Material.CHEST)
                .amount(1)
                .name(FactionsPlugin.getInstance().getConfig().getString("fvault.Item.Name"))
                .lore(FactionsPlugin.getInstance().getConfig().getStringList("fvault.Item.Lore"))
                .build();

        //check if vault is set
        if (vaultLocation != null) {
            context.msg(TL.COMMAND_GETVAULT_ALREADYSET);
            return;
        }

        //has enough money?
        int amount = FactionsPlugin.getInstance().getConfig().getInt("fvault.Price");
        if (!context.fPlayer.hasMoney(amount)) {
            return;
        }

        //success :)
        context.fPlayer.takeMoney(amount);
        context.player.getInventory().addItem(vault);
        context.fPlayer.msg(TL.COMMAND_GETVAULT_RECEIVE);

    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_GETVAULT_DESCRIPTION;
    }

}
