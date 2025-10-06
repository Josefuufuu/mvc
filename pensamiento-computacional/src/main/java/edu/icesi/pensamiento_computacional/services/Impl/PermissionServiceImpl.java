package edu.icesi.pensamiento_computacional.services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.icesi.pensamiento_computacional.model.Permission;
import edu.icesi.pensamiento_computacional.repository.PermissionRepository;
import edu.icesi.pensamiento_computacional.services.PermissionService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission updatePermission(Integer id, Permission permission) {
        Permission existingPermission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id " + id));

        existingPermission.setName(permission.getName());
        existingPermission.setDescription(permission.getDescription());

        return permissionRepository.save(existingPermission);
    }

    @Override
    public void deletePermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id " + id));
        permissionRepository.delete(permission);
    }

    @Override
    public Permission getPermissionById(Integer id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id " + id));
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }
}
