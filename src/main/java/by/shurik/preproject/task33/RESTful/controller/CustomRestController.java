package by.shurik.preproject.task33.RESTful.controller;

import by.shurik.preproject.task33.RESTful.model.Role;
import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.model.UserDto;
import by.shurik.preproject.task33.RESTful.service.RoleService;
import by.shurik.preproject.task33.RESTful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class CustomRestController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public CustomRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
        Optional<UserDto> optionalUserDto = userService.addUser(newDtoUser);
        if (optionalUserDto.isPresent()) {
            return new ResponseEntity<>(optionalUserDto.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
//
//    @GetMapping("/allUsers")
//    public List<User> getAllUsers() {
//        return userService.listUser();
//    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto updateUserDto) {
        Optional<UserDto> optionalUserDto = userService.updateUser(updateUserDto);
        if (optionalUserDto.isPresent()) {
            return new ResponseEntity<>(optionalUserDto.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
