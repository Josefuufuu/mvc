package edu.icesi.pensamiento_computacional.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.icesi.pensamiento_computacional.model.Permission;
import edu.icesi.pensamiento_computacional.repository.PermissionRepository;
import edu.icesi.pensamiento_computacional.services.Impl.PermissionServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Test
    void createPermission_persistsEntity() {
        Permission permissionToCreate = buildPermission(null, "MANAGE_USERS", "Can manage user accounts");

        when(permissionRepository.save(any(Permission.class))).thenAnswer(invocation -> {
            Permission stored = invocation.getArgument(0, Permission.class);
            stored.setId(20);
            return stored;
        });

        Permission storedPermission = permissionService.createPermission(permissionToCreate);

        assertEquals(20, storedPermission.getId());
        assertEquals(permissionToCreate.getName(), storedPermission.getName());

        ArgumentCaptor<Permission> permissionCaptor = ArgumentCaptor.forClass(Permission.class);
        verify(permissionRepository).save(permissionCaptor.capture());
        assertEquals(permissionToCreate.getName(), permissionCaptor.getValue().getName());
    }

    @Test
    void updatePermission_withValidData_updatesExistingPermission() {
        Permission existingPermission = buildPermission(5, "VIEW_CONTENT", "View learning content");
        Permission permissionChanges = buildPermission(null, "EDIT_CONTENT", "Edit learning content");

        when(permissionRepository.findById(existingPermission.getId())).thenReturn(Optional.of(existingPermission));
        when(permissionRepository.save(any(Permission.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Permission updatedPermission = permissionService.updatePermission(existingPermission.getId(), permissionChanges);

        assertEquals(permissionChanges.getName(), updatedPermission.getName());
        assertEquals(permissionChanges.getDescription(), updatedPermission.getDescription());
        verify(permissionRepository).save(existingPermission);
    }

    @Test
    void updatePermission_whenNotFound_throwsException() {
        Permission permissionChanges = buildPermission(null, "EDIT_CONTENT", "Edit learning content");

        when(permissionRepository.findById(70)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> permissionService.updatePermission(70, permissionChanges));
        verify(permissionRepository, never()).save(any(Permission.class));
    }

    @Test
    void deletePermission_existingPermission_deletesSuccessfully() {
        Permission existingPermission = buildPermission(9, "DELETE_CONTENT", "Delete learning content");

        when(permissionRepository.findById(existingPermission.getId())).thenReturn(Optional.of(existingPermission));

        permissionService.deletePermission(existingPermission.getId());

        verify(permissionRepository).delete(existingPermission);
    }

    @Test
    void deletePermission_whenNotFound_throwsException() {
        when(permissionRepository.findById(30)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> permissionService.deletePermission(30));
        verify(permissionRepository, never()).delete(any(Permission.class));
    }

    @Test
    void getPermissionById_returnsStoredPermission() {
        Permission existingPermission = buildPermission(4, "VIEW_CONTENT", "View learning content");
        when(permissionRepository.findById(existingPermission.getId())).thenReturn(Optional.of(existingPermission));

        Permission foundPermission = permissionService.getPermissionById(existingPermission.getId());

        assertNotNull(foundPermission);
        assertEquals(existingPermission.getId(), foundPermission.getId());
    }

    @Test
    void getPermissionById_whenNotFound_throwsException() {
        when(permissionRepository.findById(41)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> permissionService.getPermissionById(41));
    }

    @Test
    void getAllPermissions_delegatesToRepository() {
        Permission permission = buildPermission(1, "VIEW_CONTENT", "View learning content");
        when(permissionRepository.findAll()).thenReturn(List.of(permission));

        List<Permission> result = permissionService.getAllPermissions();

        assertEquals(List.of(permission), result);
    }

    private Permission buildPermission(Integer id, String name, String description) {
        Permission permission = new Permission();
        permission.setId(id);
        permission.setName(name);
        permission.setDescription(description);
        return permission;
    }
}
