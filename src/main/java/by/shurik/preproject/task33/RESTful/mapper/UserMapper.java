package by.shurik.preproject.task33.RESTful.mapper;

import by.shurik.preproject.task33.RESTful.dto.UserDto;
import by.shurik.preproject.task33.RESTful.model.User;

import java.util.Optional;

public interface UserMapper {
    User getUserFromDto(UserDto userDto);

    UserDto getUserDtoFromUser(User user);
}
