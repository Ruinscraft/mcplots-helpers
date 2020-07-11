package net.mcplots.helpers;

import net.mcplots.helpers.plotgivetask.MonthlyPlotGiveTask;
import net.mcplots.helpers.plotgivetask.PlotGiveTask;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpersPlugin extends JavaPlugin {

    private MonthlyPlotGiveHistory monthlyPlotGiveHistory;
    private PlotGiveTask plotGiveTask;

    public MonthlyPlotGiveHistory getMonthlyPlotGiveHistory() {
        return monthlyPlotGiveHistory;
    }

    public PlotGiveTask getPlotGiveTask() {
        return plotGiveTask;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // setup MonthlyPlotGiveHistory
        {
            String host = getConfig().getString("monthly-plot-give-history.mysql.host");
            int port = getConfig().getInt("monthly-plot-give-history.mysql.port");
            String database = getConfig().getString("monthly-plot-give-history.mysql.database");
            String username = getConfig().getString("monthly-plot-give-history.mysql.username");
            String password = getConfig().getString("monthly-plot-give-history.mysql.password");

            monthlyPlotGiveHistory = new MySQLMonthlyPlotGiveHistory(host, port, database, username, password);
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
