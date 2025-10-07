package edu.icesi.pensamiento_computacional.controller.mvc;

import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.icesi.pensamiento_computacional.controller.mvc.form.RoleForm;
import edu.icesi.pensamiento_computacional.controller.mvc.form.RolePermissionAssignmentForm;
import edu.icesi.pensamiento_computacional.model.Permission;
import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.repository.PermissionRepository;
import edu.icesi.pensamiento_computacional.services.RoleService;
import jakarta.servlet.http.HttpSession;
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
    public String showCreateRoleForm(HttpSession session, Model model) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }
        if (!model.containsAttribute("roleForm")) {
            model.addAttribute("roleForm", new RoleForm());
        }
        populatePermissions(model);
        populateRoles(model);
        return "roles/create";
    }

    @PostMapping("/create")
    public String createRole(HttpSession session,
            @Valid @ModelAttribute("roleForm") RoleForm roleForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }

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

    @GetMapping("/assign-permissions")
    public String showAssignPermissions(HttpSession session, Model model) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }
        if (!model.containsAttribute("assignmentForm")) {
            model.addAttribute("assignmentForm", new RolePermissionAssignmentForm());
        }
        populateRoles(model);
        populatePermissions(model);
        return "roles/assign-permissions";
    }

    @PostMapping("/assign-permissions")
    public String assignPermissions(HttpSession session,
            @Valid @ModelAttribute("assignmentForm") RolePermissionAssignmentForm assignmentForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }

        if (bindingResult.hasErrors()) {
            populateRoles(model);
            populatePermissions(model);
            return "roles/assign-permissions";
        }

        try {
            Role existingRole = roleService.getRoleById(assignmentForm.getRoleId());
            Role updatedRole = new Role();
            updatedRole.setName(existingRole.getName());
            updatedRole.setDescription(existingRole.getDescription());

            Set<Permission> selectedPermissions = new HashSet<>();
            assignmentForm.getPermissionIds().forEach(permissionId -> {
                Permission permission = new Permission();
                permission.setId(permissionId);
                selectedPermissions.add(permission);
            });

            updatedRole.setPermissions(selectedPermissions);
            roleService.updateRole(existingRole.getId(), updatedRole);

            redirectAttributes.addFlashAttribute("successMessage", "Permisos actualizados correctamente.");
            return "redirect:/roles/assign-permissions";
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("roleId", "role.notfound", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("permissionIds", "permission.invalid", ex.getMessage());
        }

        populateRoles(model);
        populatePermissions(model);
        return "roles/assign-permissions";
    }

    @GetMapping("/manage")
    public String showRoleManagement(HttpSession session, Model model) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }
        model.addAttribute("roles", roleService.getAllRoles());
        return "roles/manage";
    }

    @PostMapping("/{roleId}/delete")
    public String deleteRole(HttpSession session,
            @PathVariable Integer roleId,
            RedirectAttributes redirectAttributes) {
        String redirect = redirectIfNotAuthenticated(session);
        if (redirect != null) {
            return redirect;
        }
        try {
            roleService.deleteRole(roleId);
            redirectAttributes.addFlashAttribute("successMessage", "Rol eliminado correctamente.");
        } catch (EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No es posible eliminar el rol porque está asociado a usuarios.");
        }
        return "redirect:/roles/manage";
    }

    private void populatePermissions(Model model) {
        model.addAttribute("permissions", permissionRepository.findAll());
    }

    private void populateRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
    }

    private String redirectIfNotAuthenticated(HttpSession session) {
        if (session.getAttribute(AuthController.SESSION_USER_ID) == null) {
            return "redirect:/auth/login";
        }
        return null;
    }
}
