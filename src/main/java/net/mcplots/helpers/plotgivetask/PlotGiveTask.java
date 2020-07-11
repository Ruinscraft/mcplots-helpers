package net.mcplots.helpers.plotgivetask;

import net.mcplots.helpers.HelpersPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

public abstract class PlotGiveTask extends BukkitRunnable {

    private HelpersPlugin plugin;

    public PlotGiveTask(HelpersPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getLogger().info("Running plot give task...");

        getNextRun().thenAccept(nextRun -> {
            LocalDate now = LocalDate.now();

            if (nextRun.isBefore(now) || nextRun.isEqual(now)) {
                
            }
        });
    }

    public abstract CompletableFuture<LocalDate> getNextRun();

}
