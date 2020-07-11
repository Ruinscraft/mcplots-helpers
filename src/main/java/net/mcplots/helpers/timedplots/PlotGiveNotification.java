package net.mcplots.helpers.timedplots;

import java.util.UUID;

public class PlotGiveNotification {

    private final UUID mojangId;
    private final long timeOfPlotGive;

    public PlotGiveNotification(UUID mojangId, long timeOfPlotGive) {
        this.mojangId = mojangId;
        this.timeOfPlotGive = timeOfPlotGive;
    }

    public UUID getMojangId() {
        return mojangId;
    }

    public long getTimeOfPlotGive() {
        return timeOfPlotGive;
    }

}
