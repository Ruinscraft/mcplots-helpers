package net.mcplots.helpers.timedplots;

import net.mcplots.helpers.HelpersPlugin;

import java.util.Date;

public class MonthlyPlotGiveTask extends PlotGiveTask {

    public MonthlyPlotGiveTask(HelpersPlugin plugin) {
        super(plugin);
    }

    @Override
    public Date getNextRun() {
        return null;
    }

}
