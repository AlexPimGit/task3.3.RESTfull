package by.shurik.preproject.task33.RESTful.mapper.UserMapperImpl;

import by.shurik.preproject.task33.RESTful.dao.UserDao;
import by.shurik.preproject.task33.RESTful.dto.UserDto;
import by.shurik.preproject.task33.RESTful.mapper.UserMapper;
import by.shurik.preproject.task33.RESTful.model.Role;
import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapperImpl implements UserMapper {
    private RoleService roleService;
    private UserDao userDao;

    @Autowired
    public UserMapperImpl(RoleService roleService, UserDao userDao) {
        this.roleService = roleService;
        this.userDao = userDao;
    }

    @Override
    public User getUserFromDto(UserDto userDto) {
        User user = new User(userDto);
        Set<Role> newSetRoles = createRoleSet(userDto.getRoles());
        user.setRoles(newSetRoles);
        return user;
    }

    @Override
    public UserDto getUserDtoFromUser(User user) {
        Long userId = (userDao.findByUserEmail(user.getEmail())).getId();
        UserDto userDto = new UserDto(user);
        userDto.setId(userId);
        return userDto;
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
