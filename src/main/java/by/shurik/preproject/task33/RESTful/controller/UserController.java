package by.shurik.preproject.task33.RESTful.controller;


import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ModelAndView getUserPage() {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ModelAndView("user", "user", userService.findByUsername(authUser.getName()));
    }
}
