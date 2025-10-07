package edu.icesi.pensamiento_computacional.controller.mvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
import edu.icesi.pensamiento_computacional.security.UserAccountPrincipal;
import edu.icesi.pensamiento_computacional.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm());
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getInstitutionalEmail(),
                        loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String displayName = resolveDisplayName(authentication);
        redirectAttributes.addFlashAttribute("successMessage",
                "Bienvenido, " + displayName + "!");
        return "redirect:/Home";
            } catch (AuthenticationException | IllegalArgumentException ex) {
                model.addAttribute("authenticationError", "Correo o contraseña incorrectos.");
                return "auth/login";
            }
        }

    private String resolveDisplayName(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserAccountPrincipal userPrincipal) {
            String fullName = userPrincipal.getFullName();
            if (fullName != null && !fullName.isBlank()) {
                return fullName;
            }
        }
        return authentication.getName();
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
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
            RedirectAttributes redirectAttributes) {
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
            redirectAttributes.addFlashAttribute("successMessage",
                    "Cuenta creada con éxito. Ahora puedes iniciar sesión.");
            return "redirect:/auth/login";
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

    private void populateRoles(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
    }
}
