package net.mcplots.helpers.timedplots;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlotGiveHistory {

    CompletableFuture<List<PlotGiveNotification>> getNotifications(UUID mojangId);

    CompletableFuture<Void> markMonthAskNotified(UUID mojangId, int month);

}
