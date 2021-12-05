package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void add(User user) {
      if (userDao.findUserInTableUsers(user.getFirstName(), user.getLastName(), user.getEmail()) == null) {
         userDao.add(user);
      } else {
         System.out.println("Данный пользователь уже есть в таблице");
      }
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Override
   public User getUserWithCar(String model, int series) {
      User user = userDao.findUserWithCar(model, series);
      if (user != null) {
         System.out.printf("Пользователь, владеющий автомобилем модели %s и серии %d:", model, series);
         System.out.println();
         System.out.println(user);
      } else {
         System.out.printf("Пользователя, владеющего автомобилем модели %s и серии %d не найдено!", model, series);
      }
      return null;
   }
}
