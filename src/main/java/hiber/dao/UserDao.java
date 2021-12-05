package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {
   void add(User user);
   List<User> listUsers();
   User findUserInTableUsers(String firstName, String lastName, String email);
   User findUserWithCar(String model, int series);
}
