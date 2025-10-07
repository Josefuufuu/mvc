package edu.icesi.pensamiento_computacional.controller.mvc;

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

import edu.icesi.pensamiento_computacional.controller.mvc.form.RoleForm;
import edu.icesi.pensamiento_computacional.model.Permission;
import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.repository.PermissionRepository;
import edu.icesi.pensamiento_computacional.services.RoleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final PermissionRepository permissionRepository;

    @GetMapping("/create")
    public String showCreateRoleForm(Model model) {
        if (!model.containsAttribute("roleForm")) {
            model.addAttribute("roleForm", new RoleForm());
        }
        populatePermissions(model);
        return "roles/create";
    }

    @PostMapping("/create")
    public String createRole(@Valid @ModelAttribute("roleForm") RoleForm roleForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            populatePermissions(model);
            return "roles/create";
        }

        Role role = new Role();
        role.setName(roleForm.getName());
        role.setDescription(roleForm.getDescription());

        Set<Permission> selectedPermissions = new HashSet<>();
        roleForm.getPermissionIds().forEach(permissionId -> {
            Permission permission = new Permission();
            permission.setId(permissionId);
            selectedPermissions.add(permission);
        });
        role.setPermissions(selectedPermissions);

        try {
            roleService.createRole(role);
            redirectAttributes.addFlashAttribute("successMessage", "Rol creado correctamente.");
            return "redirect:/roles/create";
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("permissionIds", "permission.invalid", ex.getMessage());
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("permissionIds", "permission.notfound", ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            bindingResult.rejectValue("name", "role.duplicate", "Ya existe un rol con este nombre.");
        }

        populatePermissions(model);
        return "roles/create";
    }

    private void populatePermissions(Model model) {
        model.addAttribute("permissions", permissionRepository.findAll());
    }
}
