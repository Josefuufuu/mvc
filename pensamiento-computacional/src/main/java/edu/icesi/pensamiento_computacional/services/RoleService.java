package edu.icesi.pensamiento_computacional.services;

import java.util.List;

import edu.icesi.pensamiento_computacional.model.Role;

public interface RoleService {

    Role createRole(Role role);
    Role updateRole(Integer id, Role role);
    void deleteRole(Integer id);
    Role getRoleById(Integer id);
    List<Role> getAllRoles();
}
