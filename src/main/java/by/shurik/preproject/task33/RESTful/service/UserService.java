package by.shurik.preproject.task33.RESTful.service;

import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> addUser(UserDto user);

    Optional<UserDto> updateUser(UserDto user);

    void removeUser(Long id);

    List<User> listUser();

    User findByUsername(String name);

    User findByUserEmail(String email);
}
