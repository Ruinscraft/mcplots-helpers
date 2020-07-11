package net.mcplots.helpers;

import net.mcplots.helpers.timedplots.MySQLPlotGiveHistory;
import net.mcplots.helpers.timedplots.PlotGiveHistory;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpersPlugin extends JavaPlugin {

    private PlotGiveHistory plotGiveHistory;

    public PlotGiveHistory getPlotGiveHistory() {
        return plotGiveHistory;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // setup PlotGiveHistory
        {
            String host = getConfig().getString("");
            int port = getConfig().getInt("");
            String database = getConfig().getString("");
            String username = getConfig().getString("");
            String password = getConfig().getString("");

            plotGiveHistory = new MySQLPlotGiveHistory(host, port, database, username, password);
        }

        // start PlotGiveTask




        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {

    }

}
