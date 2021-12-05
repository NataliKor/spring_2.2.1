package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("SELECT user FROM User user");
      return query.getResultList();
   }

   @Override
   //поиск пользователя в таблице users
   public User findUserInTableUsers(String firstName, String lastName, String email) {
      User result;
      String sql = "SELECT user FROM User user WHERE user.firstName = ?1 AND user.lastName = ?2 AND user.email = ?3";
      Query query = sessionFactory.openSession().createQuery(sql);
      query.setParameter(1, firstName);
      query.setParameter(2, lastName);
      query.setParameter(3, email);
      try {
         result = (User) query.getSingleResult();
      } catch (NoResultException e) {
         result = null;
      }
      return result;
   }

   @Override //поиск user по модели и серии автомобиля
   public User findUserWithCar(String model, int series) {
      String sql = "SELECT car FROM Car car WHERE car.model = ?1 AND car.series = ?2";
      Query query = sessionFactory.openSession().createQuery(sql);
      query.setParameter(1, model);
      query.setParameter(2, series);
      Car car = (Car) query.getSingleResult();
      return car.getUser();
   }
}
