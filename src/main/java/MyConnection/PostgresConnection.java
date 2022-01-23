package MyConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {

    private Connection connection;

    private PostgresConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            setConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class Singleton {
        private static final PostgresConnection INSTANCE = new PostgresConnection();
    }

    public static PostgresConnection getInstance() {
        return Singleton.INSTANCE;
    }


    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                setConnection();
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

