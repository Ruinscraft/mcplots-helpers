package net.mcplots.helpers.timedplots;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MySQLPlotGiveHistory implements PlotGiveHistory {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public MySQLPlotGiveHistory(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Override
    public CompletableFuture<List<PlotGiveNotification>> getNotifications(UUID mojangId) {
        return CompletableFuture.supplyAsync(() -> {
            List<PlotGiveNotification> notifications = new ArrayList<>();

            try (Connection connection = getConnection()) {

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return notifications;
        });
    }

    @Override
    public CompletableFuture<Void> markMonthAskNotified(UUID mojangId, int month) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection()) {

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    // Close after using each time
    private Connection getConnection() {
        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
