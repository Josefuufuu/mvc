package edu.icesi.pensamiento_computacional.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.icesi.pensamiento_computacional.model.Permission;
import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.repository.PermissionRepository;
import edu.icesi.pensamiento_computacional.repository.RoleRepository;
import edu.icesi.pensamiento_computacional.services.Impl.RoleServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void createRole_withPermissions_persistsRole() {
        Permission persistedPermission = buildPermission(1, "MANAGE_USERS");
        Permission permissionReference = new Permission();
        permissionReference.setId(persistedPermission.getId());

        Role roleToCreate = buildRole(null, "Teacher");
        roleToCreate.setDescription("Responsible for teaching classes");
        roleToCreate.setPermissions(Set.of(permissionReference));

        when(permissionRepository.findById(persistedPermission.getId())).thenReturn(Optional.of(persistedPermission));
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> {
            Role stored = invocation.getArgument(0, Role.class);
            stored.setId(25);
            return stored;
        });

        Role storedRole = roleService.createRole(roleToCreate);

        assertEquals(25, storedRole.getId());
        assertEquals(Set.of(persistedPermission), storedRole.getPermissions());

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(roleCaptor.capture());
        assertEquals(Set.of(persistedPermission), roleCaptor.getValue().getPermissions());
    }

    @Test
    void createRole_withoutPermissions_throwsException() {
        Role roleToCreate = buildRole(null, "Assistant");
        roleToCreate.setPermissions(Collections.emptySet());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> roleService.createRole(roleToCreate));

        assertNotNull(exception.getMessage());
        verifyNoInteractions(permissionRepository);
        verifyNoInteractions(roleRepository);
    }

    @Test
    void createRole_withPermissionWithoutId_throwsException() {
        Permission permissionWithoutId = new Permission();
        permissionWithoutId.setName("MANAGE_ACTIVITY");

        Role roleToCreate = buildRole(null, "Coordinator");
        roleToCreate.setPermissions(Set.of(permissionWithoutId));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> roleService.createRole(roleToCreate));

        assertNotNull(exception.getMessage());
        verifyNoInteractions(permissionRepository);
        verifyNoInteractions(roleRepository);
    }

    @Test
    void createRole_withUnknownPermission_throwsException() {
        Permission unknownPermission = new Permission();
        unknownPermission.setId(33);

        Role roleToCreate = buildRole(null, "Coordinator");
        roleToCreate.setPermissions(Set.of(unknownPermission));

        when(permissionRepository.findById(unknownPermission.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.createRole(roleToCreate));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void updateRole_withValidData_updatesExistingRole() {
        Permission originalPermission = buildPermission(1, "MANAGE_USERS");
        Role existingRole = buildRole(8, "Assistant");
        existingRole.setDescription("Assists lead teachers");
        existingRole.setPermissions(Set.of(originalPermission));

        Permission updatedPermission = buildPermission(2, "MANAGE_CONTENT");
        Permission updatedPermissionReference = new Permission();
        updatedPermissionReference.setId(updatedPermission.getId());

        Role roleChanges = buildRole(null, "Coordinator");
        roleChanges.setDescription("Coordinates academic activities");
        roleChanges.setPermissions(Set.of(updatedPermissionReference));

        when(roleRepository.findById(existingRole.getId())).thenReturn(Optional.of(existingRole));
        when(permissionRepository.findById(updatedPermission.getId())).thenReturn(Optional.of(updatedPermission));
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Role updatedRole = roleService.updateRole(existingRole.getId(), roleChanges);

        assertEquals(roleChanges.getName(), updatedRole.getName());
        assertEquals(roleChanges.getDescription(), updatedRole.getDescription());
        assertEquals(Set.of(updatedPermission), updatedRole.getPermissions());
        verify(roleRepository).save(existingRole);
    }

    @Test
    void updateRole_withoutPermissions_throwsException() {
        Permission existingPermission = buildPermission(1, "MANAGE_USERS");
        Role existingRole = buildRole(12, "Assistant");
        existingRole.setPermissions(Set.of(existingPermission));

        when(roleRepository.findById(existingRole.getId())).thenReturn(Optional.of(existingRole));

        Role roleChanges = buildRole(null, "Assistant");
        roleChanges.setPermissions(Collections.emptySet());

        assertThrows(IllegalArgumentException.class, () -> roleService.updateRole(existingRole.getId(), roleChanges));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void updateRole_withUnknownPermission_throwsException() {
        Permission existingPermission = buildPermission(1, "MANAGE_USERS");
        Role existingRole = buildRole(40, "Assistant");
        existingRole.setPermissions(Set.of(existingPermission));

        when(roleRepository.findById(existingRole.getId())).thenReturn(Optional.of(existingRole));

        Permission unknownPermission = new Permission();
        unknownPermission.setId(77);
        Role roleChanges = buildRole(null, "Assistant");
        roleChanges.setPermissions(Set.of(unknownPermission));

        when(permissionRepository.findById(unknownPermission.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.updateRole(existingRole.getId(), roleChanges));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void updateRole_whenNotFound_throwsException() {
        Role roleChanges = buildRole(null, "Assistant");
        roleChanges.setPermissions(Set.of(buildPermission(1, "MANAGE_USERS")));

        when(roleRepository.findById(70)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.updateRole(70, roleChanges));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void deleteRole_existingRole_deletesSuccessfully() {
        Role existingRole = buildRole(3, "Teacher");
        when(roleRepository.findById(existingRole.getId())).thenReturn(Optional.of(existingRole));

        roleService.deleteRole(existingRole.getId());

        verify(roleRepository).delete(existingRole);
    }

    @Test
    void deleteRole_whenNotFound_throwsException() {
        when(roleRepository.findById(44)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.deleteRole(44));
        verify(roleRepository, never()).delete(any(Role.class));
    }

    @Test
    void getRoleById_returnsStoredRole() {
        Role existingRole = buildRole(5, "Teacher");
        when(roleRepository.findById(existingRole.getId())).thenReturn(Optional.of(existingRole));

        Role foundRole = roleService.getRoleById(existingRole.getId());

        assertNotNull(foundRole);
        assertEquals(existingRole.getId(), foundRole.getId());
    }

    @Test
    void getRoleById_whenNotFound_throwsException() {
        when(roleRepository.findById(55)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.getRoleById(55));
    }

    @Test
    void getAllRoles_delegatesToRepository() {
        Role role = buildRole(1, "Teacher");
        when(roleRepository.findAll()).thenReturn(List.of(role));

        List<Role> result = roleService.getAllRoles();

        assertEquals(List.of(role), result);
    }

    private Role buildRole(Integer id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }

    private Permission buildPermission(Integer id, String name) {
        Permission permission = new Permission();
        permission.setId(id);
        permission.setName(name);
        return permission;
    }
}
