package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("create table if not exists users(id int  not null auto_increment,firstname varchar(45) not null,lastname varchar(45) not null,age int not null,primary key (id));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("drop table if exists users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement("insert into users(firstname, lastname, age) values (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User " + name + " has been added");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement("delete from users where id = ?")) {
            statement.setInt(1, (int) id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(PreparedStatement statement = Util.getConnection().prepareStatement("select * from users");
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("delete from users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
