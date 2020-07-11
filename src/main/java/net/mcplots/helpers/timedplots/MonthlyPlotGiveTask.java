package net.mcplots.helpers.timedplots;

import net.mcplots.helpers.HelpersPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class MonthlyPlotGiveTask extends PlotGiveTask {

    private int lastMonthRun;

    private HelpersPlugin plugin;

    public MonthlyPlotGiveTask(HelpersPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public CompletableFuture<LocalDate> getNextRun() {
        return getLastMonthRun().thenApply(lastMonthRun -> {
            LocalDate now = LocalDate.now();
            int currentMonth = now.getMonthValue();

            if (lastMonthRun < currentMonth) {
                return now; // should be run now, it hasn't been run this month
            } else {
                return now.withMonth(currentMonth + 1).withDayOfMonth(1); // run the 1st day of next month
            }
        });
    }

    public CompletableFuture<Integer> getLastMonthRun() {
        return CompletableFuture.supplyAsync(() -> {
            File file = new File(plugin.getDataFolder(), "monthly-plot-give-last-month.txt");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try (Scanner scanner = new Scanner(new FileInputStream(file))) {
                if (scanner.hasNextInt()) {
                    int month = scanner.nextInt();

                    return month;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return -1;
        });
    }

}
