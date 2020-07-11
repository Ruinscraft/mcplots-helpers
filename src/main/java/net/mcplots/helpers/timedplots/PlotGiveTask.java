package net.mcplots.helpers.timedplots;

import net.mcplots.helpers.HelpersPlugin;

import java.util.Date;

public abstract class PlotGiveTask implements Runnable {

    private HelpersPlugin plugin;

    public PlotGiveTask(HelpersPlugin plugin) {
        this.plugin = plugin;
    }

    public void run() {
        plugin.getLogger().info("Running plot give task...");
    }

    public abstract Date getNextRun();

}
