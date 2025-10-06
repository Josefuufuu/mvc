package edu.icesi.pensamiento_computacional.services;

import java.util.List;

import edu.icesi.pensamiento_computacional.model.Permission;

public interface PermissionService {

    Permission createPermission(Permission permission);
    Permission updatePermission(Integer id, Permission permission);
    void deletePermission(Integer id);
    Permission getPermissionById(Integer id);
    List<Permission> getAllPermissions();
}
