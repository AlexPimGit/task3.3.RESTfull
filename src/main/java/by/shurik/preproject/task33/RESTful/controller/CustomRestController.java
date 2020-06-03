package by.shurik.preproject.task33.RESTful.controller;

import by.shurik.preproject.task33.RESTful.mapper.UserMapper;
import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.dto.UserDto;
import by.shurik.preproject.task33.RESTful.service.RoleService;
import by.shurik.preproject.task33.RESTful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
public class CustomRestController {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public CustomRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserDto newDtoUser
    ) {
        if ("".equals(newDtoUser.getName()) ||
                "".equals(newDtoUser.getPosition()) ||
                "".equals(Integer.toString(newDtoUser.getAge())) ||
                "".equals(newDtoUser.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
//        Optional<UserDto> optionalUserDto = userService.addUser(newDtoUser);
//        if (optionalUserDto.isPresent()) {
//            return new ResponseEntity<>(optionalUserDto.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.CREATED);
        Optional<User> optionalUser = userService.addUser(userMapper.getUserFromDto(newDtoUser));
        if (optionalUser.isPresent()) {
            UserDto backUserDto = userMapper.getUserDtoFromUser(optionalUser.get());
            return new ResponseEntity<>(backUserDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto updateUserDto) {
        Optional<User> optionalUser = userService.updateUser(userMapper.getUserFromDto(updateUserDto));
        if (optionalUser.isPresent()) {
            UserDto backUserDto = userMapper.getUserDtoFromUser(optionalUser.get());
            return new ResponseEntity<>(backUserDto, HttpStatus.OK);
        }
        return new ResponseEntity<>((HttpStatus.BAD_REQUEST));
//        Optional <User> optionalUser =
//        Optional<UserDto> optionalUserDto = userService.updateUser(updateUserDto);
//        if (optionalUserDto.isPresent()) {
//            return new ResponseEntity<>(optionalUserDto.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getCompanyList() {
        return new ResponseEntity<>(userService.listUser(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
