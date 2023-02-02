package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {


    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        // Создание таблицы User(ов)
        userService.createUsersTable();
        // Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        User user1 = new User("Ivan", "Ivanov", (byte) 20);
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        User user2 = new User("Petya", "Petrov", (byte) 26);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        User user3 = new User("Danil", "Danilov", (byte) 21);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        User user4 = new User("Mihail", "Mihailov", (byte) 62);
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        userService.getAllUsers();
        // Очистка таблицы User(ов)
        userService.cleanUsersTable();
        // Удаление таблицы
        userService.dropUsersTable();
    }
}
