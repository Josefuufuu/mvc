package edu.icesi.pensamiento_computacional.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.icesi.pensamiento_computacional.controller.mvc.AuthController;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {


    @GetMapping("Home")
    public String getHome(HttpSession session){
        if (session.getAttribute(AuthController.SESSION_USER_ID) != null) {
            return "redirect:/dashboard";
        }
        return "home";
    }


    @GetMapping("/dashboard")
    public String getDashboard(HttpSession session, Model model) {
        Object userId = session.getAttribute(AuthController.SESSION_USER_ID);
        if (userId == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("displayName", session.getAttribute(AuthController.SESSION_USER_NAME));
        return "dashboard";
    }


}