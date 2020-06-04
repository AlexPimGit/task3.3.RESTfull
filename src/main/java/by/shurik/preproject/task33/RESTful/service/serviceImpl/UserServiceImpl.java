package by.shurik.preproject.task33.RESTful.service.serviceImpl;

import by.shurik.preproject.task33.RESTful.dao.UserDao;
import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<User> addUser(User user) {
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        if (userDao.addUser(user)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUser(User updateUser) {
        if (updateUser.getUserPassword().startsWith("$") || updateUser.getUserPassword().equals("")) {
            updateUser.setUserPassword(userDao.findByUserEmail(updateUser.getEmail()).getPassword());
        } else {
            updateUser.setUserPassword(bCryptPasswordEncoder.encode(updateUser.getUserPassword()));
        }
        if (userDao.updateUser(updateUser)) {
            return Optional.of(updateUser);
        }
        return Optional.empty();
    }

    @Override
    public void removeUser(Long id) {
        userDao.removeUser(id);
    }

    @Override
    public List<User> listUser() {
        List<User> users = userDao.listUser();
        for (User user : users) {
            Hibernate.initialize(user.getRoles());
        }
        return users;
    }


    @Override
    public User findByUsername(String name) {
        return userDao.findByUsername(name);
    }

    @Override
    public User findByUserEmail(String email) {
        User user = userDao.findByUserEmail(email);
        if (user != null) {
            Hibernate.initialize(user.getRoles());
        }
        return user;
    }
}
