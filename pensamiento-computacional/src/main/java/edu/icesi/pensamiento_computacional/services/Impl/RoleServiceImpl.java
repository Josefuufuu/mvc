package edu.icesi.pensamiento_computacional.services.Impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.icesi.pensamiento_computacional.model.Permission;
import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.repository.PermissionRepository;
import edu.icesi.pensamiento_computacional.repository.RoleRepository;
import edu.icesi.pensamiento_computacional.services.RoleService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Role createRole(Role role) {
        role.setPermissions(loadPermissions(role.getPermissions()));
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Integer id, Role role) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));

        existingRole.setName(role.getName());
        existingRole.setDescription(role.getDescription());
        existingRole.setPermissions(loadPermissions(role.getPermissions()));

        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
        roleRepository.delete(role);
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    private Set<Permission> loadPermissions(Set<Permission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            throw new IllegalArgumentException("A role must declare at least one permission.");
        }

        return permissions.stream().map(permission -> {
            Integer permissionId = permission.getId();
            if (permissionId == null) {
                throw new IllegalArgumentException(
                        "Permission identifiers must be provided when assigning them to a role.");
            }
            return permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new EntityNotFoundException("Permission not found with id " + permissionId));
        }).collect(Collectors.toSet());
    }
}
