package net.mcplots.helpers;

import net.mcplots.helpers.timedplots.MonthlyPlotGiveTask;
import net.mcplots.helpers.timedplots.MySQLPlotGiveHistory;
import net.mcplots.helpers.timedplots.PlotGiveHistory;
import net.mcplots.helpers.timedplots.PlotGiveTask;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpersPlugin extends JavaPlugin {

    private PlotGiveHistory plotGiveHistory;
    private PlotGiveTask plotGiveTask;

    public PlotGiveHistory getPlotGiveHistory() {
        return plotGiveHistory;
    }

    public PlotGiveTask getPlotGiveTask() {
        return plotGiveTask;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // setup PlotGiveHistory
        {
            String host = getConfig().getString("plot-give-history.mysql.host");
            int port = getConfig().getInt("plot-give-history.mysql.port");
            String database = getConfig().getString("plot-give-history.mysql.database");
            String username = getConfig().getString("plot-give-history.mysql.username");
            String password = getConfig().getString("plot-give-history.mysql.password");

            plotGiveHistory = new MySQLPlotGiveHistory(host, port, database, username, password);
        }

        // setup PlotGiveTask
        {
            plotGiveTask = new MonthlyPlotGiveTask(this);

            long delayTicks = 20 * 60; // ~1 minute
            long periodTicks = 20 * 60 * 30; // ~30 minutes
            plotGiveTask.runTaskTimerAsynchronously(this, delayTicks, periodTicks);
        }

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {
        plotGiveTask.cancel();
    }

}
