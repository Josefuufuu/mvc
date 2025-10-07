package edu.icesi.pensamiento_computacional.controller.mvc;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.icesi.pensamiento_computacional.controller.mvc.form.UserRoleAssignmentForm;
import edu.icesi.pensamiento_computacional.controller.mvc.form.UserRoleRemovalForm;
import edu.icesi.pensamiento_computacional.model.UserAccount;
import edu.icesi.pensamiento_computacional.services.RoleService;
import edu.icesi.pensamiento_computacional.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;


    @GetMapping("userList")
    public String getUsers(HttpSession session, Model model){
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }
        List<UserAccount> users  = userService.getAllUsers();
        model.addAttribute("users", users);
        return ("userList");
    }

    @GetMapping("/assign-roles")
    public String showAssignRoles(HttpSession session, Model model) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }
        if (!model.containsAttribute("assignmentForm")) {
            model.addAttribute("assignmentForm", new UserRoleAssignmentForm());
        }
        populateUsersAndRoles(model);
        return "user/assign-roles";
    }

    @PostMapping("/assign-roles")
    public String assignRoles(HttpSession session,
            @Valid @ModelAttribute("assignmentForm") UserRoleAssignmentForm assignmentForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }

        if (bindingResult.hasErrors()) {
            populateUsersAndRoles(model);
            return "user/assign-roles";
        }

        try {
            userService.assignRoles(assignmentForm.getUserId(), assignmentForm.getRoleIds());
            redirectAttributes.addFlashAttribute("successMessage", "Roles actualizados correctamente.");
            return "redirect:/users/assign-roles";
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("userId", "user.notfound", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("roleIds", "role.invalid", ex.getMessage());
        }

        populateUsersAndRoles(model);
        return "user/assign-roles";
    }

    @GetMapping("/remove-roles")
    public String showRemoveRoles(HttpSession session, Model model) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }
        if (!model.containsAttribute("removalForm")) {
            model.addAttribute("removalForm", new UserRoleRemovalForm());
        }
        populateUsersAndRoles(model);
        return "user/remove-roles";
    }

    @PostMapping("/remove-roles")
    public String removeRoles(HttpSession session,
            @Valid @ModelAttribute("removalForm") UserRoleRemovalForm removalForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }

        if (bindingResult.hasErrors()) {
            populateUsersAndRoles(model);
            return "user/remove-roles";
        }

        try {
            userService.removeRoles(removalForm.getUserId(), removalForm.getRoleIds());
            redirectAttributes.addFlashAttribute("successMessage", "Roles eliminados correctamente del usuario.");
            return "redirect:/users/remove-roles";
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("userId", "user.notfound", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("roleIds", "role.invalid", ex.getMessage());
        }

        populateUsersAndRoles(model);
        return "user/remove-roles";
    }

    private void populateUsersAndRoles(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
    }

    private String redirectIfNotAuthenticated(HttpSession session) {
        if (session.getAttribute(AuthController.SESSION_USER_ID) == null) {
            return "redirect:/auth/login";
        }
        return null;
    }
}
