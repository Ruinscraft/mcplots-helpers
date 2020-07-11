package net.mcplots.helpers.plotgivetask;

import net.mcplots.helpers.HelpersPlugin;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class MonthlyPlotGiveTask extends PlotGiveTask {

    private HelpersPlugin plugin;

    public MonthlyPlotGiveTask(HelpersPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void run() {
        super.run();
        updateLastMonthRun();
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

    public CompletableFuture<Void> updateLastMonthRun() {
        return CompletableFuture.runAsync(() -> {
            File file = new File(plugin.getDataFolder(), "monthly-plot-give-last-month.txt");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            LocalDate now = LocalDate.now();

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(now.getMonth().getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
