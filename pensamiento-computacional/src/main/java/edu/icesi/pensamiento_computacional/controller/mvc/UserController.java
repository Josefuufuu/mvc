package edu.icesi.pensamiento_computacional.controller.mvc;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.icesi.pensamiento_computacional.model.UserAccount;
import edu.icesi.pensamiento_computacional.services.UserService;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final  UserService userService;

 
    @GetMapping("userList")
    public String getUsers(Model model){
        List<UserAccount> users  = userService.getAllUsers();
        model.addAttribute("users", users);
        return ("userList");
    }
}
