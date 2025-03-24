package jm.task.core.jdbc.dao;

import com.mysql.cj.xdevapi.PreparableStatement;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUsersTable() {
        String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS user(Id INT AUTO_INCREMENT PRIMARY KEY, name TEXT, lastName TEXT, age TINYINT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUsersTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        String dropUsersTableQuery = "DROP TABLE IF EXISTS user";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropUsersTableQuery);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveUserQuery = "INSERT into user(name, lastName, age) values (?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(saveUserQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String removeUserByIdQuery = "DELETE FROM user WHERE Id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(removeUserByIdQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        String getAllUsersQuery = "SELECT * FROM user";
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(getAllUsersQuery);
            List<User> users = new ArrayList<User>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
                System.out.println(user.toString());
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void cleanUsersTable() {
        String cleanUsersTableQuery = "DELETE FROM user";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanUsersTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
