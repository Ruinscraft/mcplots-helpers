package net.mcplots.helpers;

import com.plotsquared.core.player.PlotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePlotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("/giveplot <name>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            sender.sendMessage(args[0] + " is not online.");
            return true;
        }

        givePlot(target);

        sender.sendMessage("Gave " + target.getName() + " one extra plot");

        return true;
    }

    public static void givePlot(Player player) {
        PlotPlayer plotPlayer = PlotPlayer.wrap(player);
        int currentLimit = plotPlayer.getAllowedPlots();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getUniqueId() + " permission set plots.plot." + (currentLimit + 1) + " true server=mcplots");
    }

}
