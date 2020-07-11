package net.mcplots.helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MySQLMonthlyPlotGiveHistory implements MonthlyPlotGiveHistory {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public MySQLMonthlyPlotGiveHistory(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute("CREATE TABLE IF NOT EXISTS monthly_plot_give_history (" +
                            "mojang_id VARCHAR(36) NOT NULL, " +
                            "time BIGINT NOT NULL);");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<List<Long>> getMonthlyPlotGiveHistory(UUID mojangId) {
        return CompletableFuture.supplyAsync(() -> {
            List<Long> history = new ArrayList<>();

            try (Connection connection = getConnection()) {
                try (PreparedStatement query = connection.prepareStatement("SELECT time FROM monthly_plot_give_history WHERE mojang_id = ?;")) {
                    query.setString(1, mojangId.toString());

                    try (ResultSet result = query.executeQuery()) {
                        while (result.next()) {
                            long time = result.getLong("time");

                            history.add(time);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return history;
        });
    }

    @Override
    public CompletableFuture<Void> addMonthlyPlotGive(UUID mojangId, long time) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                try (PreparedStatement insert = connection.prepareStatement("INSERT INTO monthly_plot_give_history (mojang_id, time) VALUES (?, ?);")) {
                    insert.setString(1, mojangId.toString());
                    insert.setLong(2, time);
                    insert.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // Close when done
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
