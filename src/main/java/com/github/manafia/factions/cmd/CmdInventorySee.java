package com.github.manafia.factions.cmd;

import com.cryptomorin.xseries.XMaterial;
import com.github.manafia.factions.FPlayer;
import com.github.manafia.factions.FPlayers;
import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.struct.Permission;
import com.github.manafia.factions.struct.Role;
import com.github.manafia.factions.zcore.fperms.Access;
import com.github.manafia.factions.zcore.fperms.PermissableAction;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class CmdInventorySee extends FCommand {

    /**
     * @author Driftay
     */

    public CmdInventorySee() {
        super();

        this.aliases.addAll(Aliases.invsee);

        this.requiredArgs.add("member name");

        this.requirements = new CommandRequirements.Builder(Permission.INVSEE)
                .playerOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!FactionsPlugin.getInstance().getConfig().getBoolean("f-inventory-see.Enabled")) {
            context.msg(TL.GENERIC_DISABLED, "Inventory See");
            return;
        }

        Access use = context.fPlayer.getFaction().getAccess(context.fPlayer, PermissableAction.TERRITORY);
        if (use == Access.DENY || (use == Access.UNDEFINED && !context.assertMinRole(Role.MODERATOR))) {
            context.msg(TL.GENERIC_NOPERMISSION, "see other faction members inventories");
            return;
        }

        ArrayList<Player> fplayers = context.fPlayer.getFaction().getOnlinePlayers();

        FPlayer targetInv = context.argAsFPlayer(0);
        if (targetInv == null || !fplayers.contains(targetInv.getPlayer())) {
            context.msg(TL.PLAYER_NOT_FOUND, Objects.requireNonNull(targetInv.getName()));
            return;
        }

        context.player.openInventory(createCopy(targetInv.getPlayer()));
    }


    public Inventory createCopy(Player player) {
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        Inventory inventory = Bukkit.createInventory(null, player.getInventory().getSize() + 9, fPlayer.getNameAndTag() + "'s Player Inventory");
        ItemStack[] armor = Objects.requireNonNull(player.getEquipment()).getArmorContents();
        ItemStack[] items = player.getInventory().getContents();
        for (int i = 0; i < items.length; ++i) {
            ItemStack item = items[i];
            if (item != null && item.getType() != Material.AIR) {
                item = item.clone();
            }
            inventory.setItem(i, item);
        }
        for (int slot = inventory.getSize() - 9; slot < inventory.getSize(); ++slot) {
            ItemStack item = inventory.getItem(slot);
            if (item == null || item.getType() == Material.AIR) {
                inventory.setItem(slot, XMaterial.GRAY_STAINED_GLASS_PANE.parseItem());
            }
        }
        if (FactionsPlugin.getInstance().version == 8) {
            inventory.setItem(inventory.getSize() - 7, armor[3]);
            inventory.setItem(inventory.getSize() - 6, armor[2]);
            inventory.setItem(inventory.getSize() - 4, armor[1]);
            inventory.setItem(inventory.getSize() - 3, armor[0]);
        }
        return inventory;
    }


    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_INVENTORYSEE_DESCRIPTION;
    }
}
