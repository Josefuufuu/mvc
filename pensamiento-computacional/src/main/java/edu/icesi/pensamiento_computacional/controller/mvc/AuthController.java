package edu.icesi.pensamiento_computacional.controller.mvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.icesi.pensamiento_computacional.controller.mvc.form.LoginForm;
import edu.icesi.pensamiento_computacional.controller.mvc.form.UserRegistrationForm;
import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.model.UserAccount;
import edu.icesi.pensamiento_computacional.repository.RoleRepository;
import edu.icesi.pensamiento_computacional.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public static final String SESSION_USER_ID = "currentUserId";
    public static final String SESSION_USER_NAME = "currentUserName";

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {
        if (session.getAttribute(SESSION_USER_ID) != null) {
            return "redirect:/dashboard";
        }
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm());
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        try {
            UserAccount authenticatedUser = userService.authenticate(
                    loginForm.getInstitutionalEmail(),
                    loginForm.getPassword());

            String displayName = authenticatedUser.getFullName();
            if (displayName == null || displayName.isBlank()) {
                displayName = authenticatedUser.getInstitutionalEmail();
            }

            redirectAttributes.addFlashAttribute("successMessage",
                    "Bienvenido, " + displayName + "!");
            return "redirect:/Home";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("authenticationError", "Correo o contraseña incorrectos.");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        if (session.getAttribute(SESSION_USER_ID) != null) {
            return "redirect:/dashboard";
        }
        if (!model.containsAttribute("registrationForm")) {
            model.addAttribute("registrationForm", new UserRegistrationForm());
        }
        populateRoles(model);
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registrationForm") UserRegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            populateRoles(model);
            return "auth/register";
        }

        Set<Role> selectedRoles = new HashSet<>();
        roleRepository.findAllById(registrationForm.getRoleIds()).forEach(selectedRoles::add);

        if (selectedRoles.isEmpty()) {
            bindingResult.rejectValue("roleIds", "roles.empty", "Selecciona al menos un rol válido.");
            populateRoles(model);
            return "auth/register";
        }

        try {
            UserAccount newUser = new UserAccount();
            newUser.setFullName(registrationForm.getFullName());
            newUser.setInstitutionalEmail(registrationForm.getInstitutionalEmail());
            newUser.setPasswordHash(registrationForm.getPassword());
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setRoles(selectedRoles);

            userService.createUser(newUser);
            UserAccount authenticated = userService.authenticate(registrationForm.getInstitutionalEmail(),
                    registrationForm.getPassword());
            session.setAttribute(SESSION_USER_ID, authenticated.getId());
            session.setAttribute(SESSION_USER_NAME,
                    authenticated.getFullName() != null && !authenticated.getFullName().isBlank()
                            ? authenticated.getFullName()
                            : authenticated.getInstitutionalEmail());
            redirectAttributes.addFlashAttribute("successMessage",
                    "Cuenta creada con éxito. ¡Bienvenido!");
            return "redirect:/dashboard";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("registrationError", "El correo institucional ya está registrado.");
            populateRoles(model);
            return "auth/register";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("registrationError", ex.getMessage());
            populateRoles(model);
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Has cerrado sesión correctamente.");
        return "redirect:/Home";
    }

    private void populateRoles(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
    }
}
