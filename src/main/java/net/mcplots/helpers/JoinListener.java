package net.mcplots.helpers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("group.sponsor")) {
            player.sendMessage(ChatColor.GRAY + "Thank for your " + ChatColor.DARK_PURPLE + "Sponsorship" + ChatColor.GRAY + "!");
            // TODO: show benefits
        }

        if (player.hasPermission("mcplots.increasedrenderdistance")) {
            player.setViewDistance(8);
        }





        // By logging in, you've claimed your free plot for the month
    }

}
