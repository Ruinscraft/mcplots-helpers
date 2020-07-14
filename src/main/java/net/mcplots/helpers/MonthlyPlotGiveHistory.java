package net.mcplots.helpers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class MonthlyPlotGiveHistory {

    public abstract CompletableFuture<List<Long>> getMonthlyPlotGiveHistory(UUID mojangId);

    public abstract CompletableFuture<Void> addMonthlyPlotGive(UUID mojangId, long time);

    public CompletableFuture<Boolean> hasRecievedPlotForMonth(UUID mojangId, int month) {
        return getMonthlyPlotGiveHistory(mojangId).thenApply(result -> {


            return false;
        });
    }

}
