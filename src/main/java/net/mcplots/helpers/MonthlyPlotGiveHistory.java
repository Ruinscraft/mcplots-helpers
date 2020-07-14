package net.mcplots.helpers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class MonthlyPlotGiveHistory {

    public abstract CompletableFuture<List<Long>> getMonthlyPlotGiveHistory(UUID mojangId);

    public abstract CompletableFuture<Void> addMonthlyPlotGive(UUID mojangId, long time);

    // Note: of current year
    public CompletableFuture<Boolean> hasRecievedPlotForMonth(UUID mojangId, int month) {
        return getMonthlyPlotGiveHistory(mojangId).thenApply(result -> {
            LocalDate now = LocalDate.now();

            for (long time : result) {
                LocalDate dateOfPlotRecv = LocalDate.ofEpochDay(time);

                // if current year
                if (dateOfPlotRecv.getYear() == now.getYear()) {
                    // if requested month
                    if (dateOfPlotRecv.getMonth().getValue() == month) {
                        return true;
                    }
                }
            }

            return false;
        });
    }

    public CompletableFuture<Boolean> hasReceievedPlotThisMonth(UUID mojangId) {
        LocalDate now = LocalDate.now();

        return hasRecievedPlotForMonth(mojangId, now.getMonth().getValue());
    }

}
