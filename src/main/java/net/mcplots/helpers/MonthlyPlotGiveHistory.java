package net.mcplots.helpers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class MonthlyPlotGiveHistory {

    public abstract CompletableFuture<List<Long>> getMonthlyPlotGiveHistory(UUID mojangId);

    public abstract CompletableFuture<Void> addMonthlyPlotGive(UUID mojangId, long time);

    // Note: of current year
    public CompletableFuture<Boolean> hasRecievedPlotForMonth(UUID mojangId, int month) {
        final LocalDate now = LocalDate.now();

        return getMonthlyPlotGiveHistory(mojangId).thenApplyAsync(result -> {
            try {
                for (long time : result) {
                    LocalDate dateOfPlotRecv = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();

                    // if current year
                    if (dateOfPlotRecv.getYear() == now.getYear()) {
                        // if requested month
                        if (dateOfPlotRecv.getMonth().getValue() == month) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        });
    }

    public CompletableFuture<Boolean> hasReceievedPlotThisMonth(UUID mojangId) {
        LocalDate now = LocalDate.now();

        return hasRecievedPlotForMonth(mojangId, now.getMonth().getValue());
    }

}
