package com.github.manafia.factions.cmd.tnt;

import com.github.manafia.factions.cmd.tnt.tntprovider.TNTProvider;
import com.github.manafia.factions.zcore.util.TL;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

/**
 * @author Saser
 */
public class TNTFillTask extends BukkitRunnable {

    private static final int FILLS_PER_ITERATION = 2000;

    private final CmdTntFill cmdTntFill;
    private final TNTProvider tntProvider;
    private final Queue<Block> dispensers;
    private final int count;
    private final int initialSize;

    private boolean isRunning = true;

    public TNTFillTask(CmdTntFill cmdTntFill, TNTProvider tntProvider, Collection<Block> dispensers, int count) {
        this.cmdTntFill = cmdTntFill;
        this.tntProvider = tntProvider;
        this.dispensers = new ArrayDeque<>(dispensers);
        this.count = count;
        this.initialSize = dispensers.size();
    }

    public boolean isCancelled() {
        return !isRunning;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        this.isRunning = false;
    }

    @Override
    public void run() {
        if (dispensers.isEmpty() || !tntProvider.isAvailable()) {
            tntProvider.sendMessage(TL.COMMAND_TNTFILL_SUCCESS.toString().replace("{amount}", (initialSize * count) + "").replace("{dispensers}", initialSize + ""));
            cancel();
            return;
        }

        int maxIters = Math.min(FILLS_PER_ITERATION, dispensers.size());

        for (int i = 0; i < maxIters; i++) {
            Block block = dispensers.poll();
            BlockState blockState = block.getState();
            if (!(blockState instanceof Dispenser)) continue;
            Dispenser dispenser = (Dispenser) blockState;

            int canBeAdded = cmdTntFill.getAddable(dispenser.getInventory(), Material.TNT);
            if (canBeAdded <= 0) continue;
            int toAdd = Math.min(canBeAdded, count);

            if (toAdd > tntProvider.getTnt()) {
                tntProvider.sendMessage(TL.COMMAND_TNT_WIDTHDRAW_NOTENOUGH_TNT.toString());
                cancel();
                return;
            }

            tntProvider.takeTnt(toAdd);
            dispenser.getInventory().addItem(new ItemStack(Material.TNT, toAdd));
        }
    }
}
