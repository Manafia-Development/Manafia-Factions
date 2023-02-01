package com.github.manafia.factions.addon;

import com.github.manafia.factions.FactionsPlugin;
import com.github.manafia.factions.cmd.FCommand;
import com.github.manafia.factions.util.Logger;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Set;

/**
 * @author SavageLabs Team
 */

public abstract class FactionsAddon {

    //Adding our custom config impl to addons?

    private String addonName;
    private FactionsPlugin plugin;

    public FactionsAddon(final FactionsPlugin plugin) {

        this.plugin = plugin;
        this.addonName = getClass().getName();

        enableAddon();

    }

    private void enableAddon() {
        onEnable();

        for (Listener listener : listenersToRegister()) {

            if (listener != null) {

                plugin.getServer().getPluginManager().registerEvents(listener, plugin);

            }

        }

        for (FCommand fCommand : fCommandsToRegister()) {

            if (fCommand != null) {

                plugin.cmdBase.addSubCommand(fCommand);

            }

        }

        Logger.print("Addon: " + getAddonName() + " loaded successfully!" , Logger.PrefixType.DEFAULT);
    }

    public void disableAddon() {

        for (Listener listener : listenersToRegister()) {

            HandlerList.unregisterAll(listener);

        }

        onDisable();

    }


    /**
     * Method called when addon enabled.
     */
    public abstract void onEnable();

    /**
     * Method called when addon disabled.
     */
    public abstract void onDisable();

    /**
     * Method to define listeners you want to register. You don't need to register them.
     * @return Set of listeners you want to register.
     */
    public abstract Set<Listener> listenersToRegister();

    /**
     * Method to define FCommands you want to register. You don't need to register them.
     * @return Set of commands you want to register.
     */
    public abstract Set<FCommand> fCommandsToRegister();


    /**
     * Addon name
     * @return Addon name.
     */
    public String getAddonName() {
        return addonName;
    }

    public FactionsPlugin getPlugin() {
        return plugin;
    }
}
