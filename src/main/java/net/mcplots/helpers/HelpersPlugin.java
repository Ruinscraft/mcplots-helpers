package net.mcplots.helpers;

import org.bukkit.plugin.java.JavaPlugin;

public class HelpersPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {

    }

}
