package com.massivecraft.factions.util;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FactionsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;


/*
 * reference diagram, task should move in this pattern out from chunk 0 in the center.
 *  8 [>][>][>][>][>] etc.
 * [^][6][>][>][>][>][>][6]
 * [^][^][4][>][>][>][4][v]
 * [^][^][^][2][>][2][v][v]
 * [^][^][^][^][0][v][v][v]
 * [^][^][^][1][1][v][v][v]
 * [^][^][3][<][<][3][v][v]
 * [^][5][<][<][<][<][5][v]
 * [7][<][<][<][<][<][<][7]
 */

public abstract class SpiralTask implements Runnable {

    // general task-related reference data
    private final transient World world;
    private final transient int limit;
    private transient boolean readyToGo = false;
    private transient int taskID = -1;
    // values for the spiral pattern routine
    private transient int x = 0;
    private transient int z = 0;
    private transient boolean isZLeg = false;
    private transient boolean isNeg = false;
    private transient int length = -1;
    private transient int current = 0;

    @SuppressWarnings("LeakingThisInConstructor")
    public SpiralTask(FLocation fLocation, int radius) {
        // limit is determined based on spiral leg length for given radius; see insideRadius()
        this.limit = (radius - 1) * 2;

        this.world = Bukkit.getWorld(fLocation.getWorldName());
        if (this.world == null) {
            FactionsPlugin.getInstance().log(Level.WARNING, "[SpiralTask] A valid world must be specified!");
            this.stop();
            return;
        }

        this.x = (int) fLocation.getX();
        this.z = (int) fLocation.getZ();

        this.readyToGo = true;

        // get this party started
        this.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FactionsPlugin.getInstance(), this, 2, 2));
    }

    public final void setTaskID(int ID) {
        if (ID == -1)
            this.stop();
        taskID = ID;
    }

    // we're done, whether finished or cancelled
    public final void stop() {
        if (!this.valid())
            return;

        readyToGo = false;
        Bukkit.getServer().getScheduler().cancelTask(taskID);
        taskID = -1;
    }

    // is this task still valid/workable?
    public final boolean valid() {
        return taskID != -1;
    }

    private static long now() {
        return System.currentTimeMillis();
    }

    /*
     * This is where the necessary work is done; you'll need to override this method with whatever you want
     * done at each chunk in the spiral pattern.
     * Return false if the entire task needs to be aborted, otherwise return true to continue.
     */
    public abstract boolean work() throws ExecutionException, InterruptedException;

    /*
     * Returns an FLocation pointing at the current chunk X and Z values.
     */
    public final FLocation currentFLocation() {
        return new FLocation(world.getName(), x, z);
    }



    /*
     * Below are the guts of the class, which you normally wouldn't need to mess with.
     */

    public final CompletableFuture getFutureTask() {
        return new CompletableFuture();
    }

    /*
     * Returns a Location pointing at the current chunk X and Z values.
     * note that the Location is at the corner of the chunk, not the center.
     */
    public final Location currentLocation() {
        return new Location(world, FLocation.chunkToBlock(x), 65.0, FLocation.chunkToBlock(z));
    }

    /*
     * Returns current chunk X and Z values.
     */
    public final int getX() {
        return x;
    }

    public final int getZ() {
        return z;
    }

    void whileTask() throws ExecutionException, InterruptedException {
        long loopStartTime = now();
        while (now() < loopStartTime + 20) {
            if (!this.work()) {
                this.finish();
                return;
            }
            if (!this.moveToNext())
                return;
        }
    }

    public final void run() {
        if (!this.valid() || !readyToGo) return;
        readyToGo = false;
        if (!this.insideRadius()) return;
        try {
            whileTask();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        readyToGo = true;
    }

    // step through chunks in spiral pattern from center; returns false if we're done, otherwise returns true
    public final boolean moveToNext() {
        if (!this.valid())
            return false;
        if (current < length) {
            current++;
            if (!this.insideRadius())
                return false;
        } else {
            current = 0;
            isZLeg ^= true;
            if (isZLeg) {
                isNeg ^= true;
                length++;
            }
        }

        // move one chunk further in the appropriate direction
        if (isZLeg)
            z += (isNeg) ? -1 : 1;
        else
            x += (isNeg) ? -1 : 1;
        return true;
    }

    public final boolean insideRadius() {
        boolean inside = current < limit;
        if (!inside)
            this.finish();
        return inside;
    }

    // for successful completion
    public void finish() {
//		FactionsPlugin.getInstance().log("SpiralTask successfully completed!");
        this.stop();
    }
}
