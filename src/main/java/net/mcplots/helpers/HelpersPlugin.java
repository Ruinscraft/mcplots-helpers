package net.mcplots.helpers;

import org.bukkit.plugin.java.JavaPlugin;

public class HelpersPlugin extends JavaPlugin {

    private MonthlyPlotGiveHistory monthlyPlotGiveHistory;

    public MonthlyPlotGiveHistory getMonthlyPlotGiveHistory() {
        return monthlyPlotGiveHistory;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupMonthlyPlotGiveHistory();

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public void onDisable() {
    }

    private void setupMonthlyPlotGiveHistory() {
        String host = getConfig().getString("monthly-plot-give-history.mysql.host");
        int port = getConfig().getInt("monthly-plot-give-history.mysql.port");
        String database = getConfig().getString("monthly-plot-give-history.mysql.database");
        String username = getConfig().getString("monthly-plot-give-history.mysql.username");
        String password = getConfig().getString("monthly-plot-give-history.mysql.password");

        monthlyPlotGiveHistory = new MySQLMonthlyPlotGiveHistory(host, port, database, username, password);
    }

}
