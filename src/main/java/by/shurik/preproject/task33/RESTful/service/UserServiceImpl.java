package by.shurik.preproject.task33.RESTful.service;

import by.shurik.preproject.task33.RESTful.dao.UserDao;
import by.shurik.preproject.task33.RESTful.model.Role;
import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.model.UserDto;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @Override
    public Optional<UserDto> addUser(UserDto userDto) {

        userDto.setUserPassword(bCryptPasswordEncoder.encode(userDto.getUserPassword()));//(пришел с пустым паролем - назначаем и кодируем пароль в байкрипту
        Set<Role> newSetRoles = createRoleSet(userDto.getRoles());//делаем роли: из массива строк создаем множество с объектами ролей
        User user = new User(userDto);//делаем Юзера из транспорта
        user.setRoles(newSetRoles);//добавляем роли в виде множества
        if (userDao.addUser(user)) { //если юзер сохранен, то делаем обратно ДТО
            user = userDao.findByUserEmail(user.getEmail());
            userDto.setId(user.getId());
            userDto.setUserPassword("");
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> updateUser(UserDto updateUserDto) {
        if (updateUserDto.getUserPassword().startsWith("$") || updateUserDto.getUserPassword().equals("")) {
            updateUserDto.setUserPassword(userDao.findByUserEmail(updateUserDto.getEmail()).getPassword());
        } else {
            updateUserDto.setUserPassword(bCryptPasswordEncoder.encode(updateUserDto.getUserPassword()));
        }
        User user = new User(updateUserDto);
        Set<Role> updateSetRoles = createRoleSet(updateUserDto.getRoles());
        user.setRoles(updateSetRoles);
        if (userDao.updateUser(user)) {
            updateUserDto.setUserPassword("");
            return Optional.of(updateUserDto);
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

    private Set<Role> createRoleSet(String[] allRoles) {
        Set<Role> roles = new HashSet<>();
        if (Arrays.asList(allRoles).contains(roleService.getRoleById(1L).getName())) {
            roles.add(roleService.getRoleById(1L));
        }
        if (Arrays.asList(allRoles).contains(roleService.getRoleById(2L).getName())) {
            roles.add(roleService.getRoleById(2L));
        }
        return roles;
    }
}
