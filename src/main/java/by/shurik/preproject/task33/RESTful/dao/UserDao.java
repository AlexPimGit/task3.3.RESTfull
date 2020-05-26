package by.shurik.preproject.task33.RESTful.dao;

import by.shurik.preproject.task33.RESTful.model.User;

import java.util.List;


public interface UserDao {
    boolean addUser(User user);

    boolean updateUser(User user);

    void removeUser(Long id);

    List listUser();

    User findByUsername(String name);

    User findByUserEmail(String email);
}
