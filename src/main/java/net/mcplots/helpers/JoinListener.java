package net.mcplots.helpers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private HelpersPlugin plugin;

    public JoinListener(HelpersPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("group.sponsor")) {
            handleSponsorJoin(event);
        }
    }

    private void handleSponsorJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(ChatColor.GRAY + "Thank you for your " + ChatColor.DARK_PURPLE + "Sponsorship" + ChatColor.GRAY + "!");
        // TODO: show benefits

        player.setViewDistance(8);

        plugin.getMonthlyPlotGiveHistory().hasReceievedPlotThisMonth(player.getUniqueId()).thenAccept(recvPlotThisMonth -> {
           if (!recvPlotThisMonth) {
               plugin.getLogger().info("Giving " + player.getName() + " an additional (monthly reward) plot for being a Sponsor");

               if (player.isOnline()) { // check if online, because we ran this async
                   player.sendMessage(ChatColor.GREEN + "You have received an additional plot for this month for being a " + ChatColor.DARK_PURPLE + "Sponsor" + ChatColor.GREEN + "!");
               }

               plugin.getMonthlyPlotGiveHistory().addMonthlyPlotGive(player.getUniqueId(), System.currentTimeMillis()).thenRun(() -> {
                   plugin.getServer().getScheduler().runTask(plugin, () -> {
                       plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "plot give " + player.getName());
                   });
               });
           }
        });
    }

}
