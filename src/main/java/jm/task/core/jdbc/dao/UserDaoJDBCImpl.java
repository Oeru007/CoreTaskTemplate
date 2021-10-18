package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private List<User> users;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getMySQLConnection().createStatement()) {
            String create = "create TABLE users " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            statement.execute(create);
        } catch (SQLException ignore) {

        }

    }

    public void dropUsersTable() {
        try (Statement statement = Util.getMySQLConnection().createStatement()) {
            String drop = "drop table users";
            statement.execute(drop);
        } catch (SQLException ignore) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String save = "insert into users(name, lastName,age) values (?, ?, ?)";
        try (PreparedStatement statement = Util.getMySQLConnection().prepareStatement(save)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException ignore) {

        }
    }

    public void removeUserById(long id) {
        String delete = "delete from users where id = ? ";
        try (PreparedStatement statement = Util.getMySQLConnection().prepareStatement(delete)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ignore) {

        }
    }

    public List<User> getAllUsers() {

        try (Statement statement = Util.getMySQLConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("select * from users")) {
            users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException ignore) {

        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getMySQLConnection().createStatement()) {
            String clean = "truncate table users";
            statement.execute(clean);
        } catch (SQLException ignore) {

        }
    }
}
