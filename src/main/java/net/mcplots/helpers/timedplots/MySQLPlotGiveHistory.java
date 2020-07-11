package net.mcplots.helpers.timedplots;

import java.sql.*;
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

        // create table
        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute("CREATE TABLE IF NOT EXISTS plot_give_notifications (" +
                            "mojang_id VARCHAR(36) NOT NULL, " +
                            "time_of_plot_give BIGINT NOT NULL);");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<List<PlotGiveNotification>> getNotifications(UUID mojangId) {
        return CompletableFuture.supplyAsync(() -> {
            List<PlotGiveNotification> notifications = new ArrayList<>();

            try (Connection connection = getConnection()) {
                try (PreparedStatement query = connection.prepareStatement("SELECT * FROM plot_give_notifications WHERE mojang_id = ?;")) {
                    query.setString(1, mojangId.toString());

                    try (ResultSet result = query.executeQuery()) {
                        while (result.next()) {
                            long timeOfPlotGive = result.getLong("time_of_plot_give");
                            PlotGiveNotification notification = new PlotGiveNotification(mojangId, timeOfPlotGive);

                            notifications.add(notification);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return notifications;
        });
    }

    @Override
    public CompletableFuture<Void> deleteNotifications(UUID mojangId) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                try (PreparedStatement delete = connection.prepareStatement("DELETE FROM plot_give_notifications WHERE mojang_id = ?;")) {
                    delete.setString(1, mojangId.toString());
                    delete.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
