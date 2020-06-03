package by.shurik.preproject.task33.RESTful.dao.daoImpl;


import by.shurik.preproject.task33.RESTful.dao.UserDao;
import by.shurik.preproject.task33.RESTful.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class UserDaoImpl implements UserDao {
    private Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());
    @PersistenceContext
    private EntityManager entityManager;

    public UserDaoImpl() {
    }

    @Override
    public boolean addUser(User user) {
        try {
            entityManager.persist(user);
            LOGGER.log(Level.INFO, "User successfully saved. User details: " + user);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateUser(User user) {
        try {
            entityManager.merge(user);//
            LOGGER.log(Level.INFO, "User successfully updated. User details: " + user);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeUser(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
        LOGGER.log(Level.INFO, "User successfully deleted. User details: " + user);
    }

    @Override
    public List<User> listUser() {
        List<User> userList = entityManager.createQuery("FROM User").getResultList();
        for (User user : userList) {
            LOGGER.log(Level.INFO, "User list: " + user);
        }
        return userList;
    }

    @Override
    public User findByUsername(String name) {
        String hql = "FROM User user WHERE user.name= :name";
        User user = (User) entityManager.createQuery(hql).setParameter("name", name).getSingleResult();
        return user;
    }

    @Override
    public User findByUserEmail(String email) {
        String hql = "FROM User user WHERE user.email= :email";
        User user = (User) entityManager.createQuery(hql).setParameter("email", email).getSingleResult();
        return user;
    }
}
