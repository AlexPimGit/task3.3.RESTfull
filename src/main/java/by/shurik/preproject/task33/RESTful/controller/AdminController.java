package by.shurik.preproject.task33.RESTful.controller;


import by.shurik.preproject.task33.RESTful.model.User;
import by.shurik.preproject.task33.RESTful.service.RoleService;
import by.shurik.preproject.task33.RESTful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

//    @Autowired
//    public AdminController(UserService userService) {
//        this.userService = userService;
//    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String getWelcome(Model model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findByUserEmail(authUser.getEmail()));
        model.addAttribute("users", userService.listUser());
        return "welcome";
    }

}
