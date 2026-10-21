import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repository {
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "4208");
  }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT id, username, email, password, role FROM users WHERE username = ? AND password = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new User(
                            result.getInt("id"),
                            result.getString("username"),
                            result.getString("email"),
                            result.getString("password"),
                            result.getString("role")
                    );
                }
            }
        }
        return null;
    }

    public boolean authenticate(String usernameOrEmail, String password) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE (username = ? OR email = ?) AND password = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, usernameOrEmail);
            statement.setString(2, usernameOrEmail);
            statement.setString(3, password);

            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    public void createUser(String username, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, "user");

            if (statement.executeUpdate() != 1) {
                throw new SQLException("The user account was not created.");
            }
        }
    }

}
