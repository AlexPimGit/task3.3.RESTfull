package by.shurik.preproject.task33.RESTful.controller;


import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> getUserPage(@PathVariable("email") String email) {
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByUserEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user/myrequest")
    public ResponseEntity<User> catchPostMapping(@RequestBody User object){
        System.out.println(object);
        User responseObject = new User();
        responseObject.setId(2L);
        responseObject.setName("Hello world");
        return new ResponseEntity<User>(responseObject, HttpStatus.OK);
    }

//    @RequestMapping(path = "/user", method = RequestMethod.GET)
//    public ModelAndView getUserPage() {
//        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return new ModelAndView("user", "user", userService.findByUsername(authUser.getName()));
//    }
}
