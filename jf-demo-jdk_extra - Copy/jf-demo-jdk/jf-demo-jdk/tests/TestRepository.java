import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// Подготовка на тестови данни. Истинската SELECT заявка остава в Repository.
class TestRepository extends Repository {
    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        try (Statement statement = connection.createStatement()) {
            // Временната таблица е видима само за тази връзка.
            // PostgreSQL я използва вместо реалната users и я изтрива при close().
            statement.executeUpdate("CREATE TEMP TABLE users ("
                    + "id INTEGER, username TEXT, email TEXT, password TEXT, role TEXT)");
            statement.executeUpdate("INSERT INTO pg_temp.users VALUES "
                    + "(101, 'Petko', 'petko@example.com', '1234', 'student'),"
                    + "(102, 'Maria', 'maria@example.com', '5678', 'teacher')");
            return connection;
        } catch (SQLException | RuntimeException e) {
            try { connection.close(); } catch (SQLException closeError) { e.addSuppressed(closeError); }
            throw e;
        }
    }
}
