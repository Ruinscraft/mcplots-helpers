package net.mcplots.helpers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MonthlyPlotGiveHistory {

    CompletableFuture<List<Long>> getMonthlyPlotGiveHistory(UUID mojangId);

    CompletableFuture<Void> addMonthlyPlotGive(UUID mojangId, long time);

}
