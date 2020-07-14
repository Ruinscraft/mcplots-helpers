package net.mcplots.helpers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDate;

public class JoinListener implements Listener {

    private MonthlyPlotGiveHistory monthlyPlotGiveHistory;

    public JoinListener(MonthlyPlotGiveHistory monthlyPlotGiveHistory) {
        this.monthlyPlotGiveHistory = monthlyPlotGiveHistory;
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

        player.sendMessage(ChatColor.GRAY + "Thank for your " + ChatColor.DARK_PURPLE + "Sponsorship" + ChatColor.GRAY + "!");
        // TODO: show benefits

        player.setViewDistance(8);

        int month = LocalDate.now().getMonth().getValue();

        monthlyPlotGiveHistory.hasRecievedPlotForMonth(player.getUniqueId(), month).thenAccept(result -> {
           if (!result) {
               if (player.isOnline()) { // check if online, because we ran this async
                   player.sendMessage(ChatColor.GREEN + "You have received an additional plot for this month for being a " + ChatColor.DARK_PURPLE + "Sponsor" + ChatColor.GREEN + "!");
               }

               monthlyPlotGiveHistory.addMonthlyPlotGive(player.getUniqueId(), System.currentTimeMillis());

               Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "plot give " + player.getName());
           }
        });
    }

}
