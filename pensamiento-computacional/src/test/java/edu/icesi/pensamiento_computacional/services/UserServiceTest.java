package edu.icesi.pensamiento_computacional.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.model.UserAccount;
import edu.icesi.pensamiento_computacional.repository.RoleRepository;
import edu.icesi.pensamiento_computacional.repository.UserAccountRepository;
import edu.icesi.pensamiento_computacional.services.Impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_withValidRoles_persistsUser() {
        Role persistedRole = buildRole(1, "Teacher");
        Role roleReference = new Role();
        roleReference.setId(persistedRole.getId());

        UserAccount userToCreate = buildUser(Set.of(roleReference));

        when(roleRepository.findById(persistedRole.getId())).thenReturn(Optional.of(persistedRole));
        when(userAccountRepository.save(any(UserAccount.class))).thenAnswer(invocation -> {
            UserAccount stored = invocation.getArgument(0, UserAccount.class);
            stored.setId(99);
            return stored;
        });

        UserAccount storedUser = userService.createUser(userToCreate);

        assertEquals(99, storedUser.getId());
        assertEquals(Set.of(persistedRole), storedUser.getRoles());

        ArgumentCaptor<UserAccount> savedUserCaptor = ArgumentCaptor.forClass(UserAccount.class);
        verify(userAccountRepository).save(savedUserCaptor.capture());
        assertEquals(Set.of(persistedRole), savedUserCaptor.getValue().getRoles());
    }

    @Test
    void createUser_withoutRoles_throwsException() {
        UserAccount userToCreate = buildUser(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(userToCreate));

        assertTrue(exception.getMessage().contains("role"));
        verifyNoInteractions(roleRepository);
        verifyNoInteractions(userAccountRepository);
    }

    @Test
    void createUser_withRoleWithoutId_throwsException() {
        Role roleWithoutId = new Role();
        roleWithoutId.setName("Assistant");
        UserAccount userToCreate = buildUser(Set.of(roleWithoutId));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(userToCreate));

        assertTrue(exception.getMessage().contains("Role identifiers"));
        verifyNoInteractions(roleRepository);
        verifyNoInteractions(userAccountRepository);
    }

    @Test
    void createUser_withUnknownRole_throwsException() {
        Role unknownRole = new Role();
        unknownRole.setId(42);
        UserAccount userToCreate = buildUser(Set.of(unknownRole));

        when(roleRepository.findById(unknownRole.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.createUser(userToCreate));
        verify(userAccountRepository, never()).save(any(UserAccount.class));
    }

    @Test
    void updateUser_withValidData_updatesExistingUser() {
        Role initialRole = buildRole(1, "Teacher");
        UserAccount existingUser = buildUser(Set.of(initialRole));
        existingUser.setId(5);
        existingUser.setProfilePhotoUrl("http://old-photo");

        Role updatedRole = buildRole(2, "Coordinator");
        Role updatedRoleReference = new Role();
        updatedRoleReference.setId(updatedRole.getId());

        UserAccount userChanges = buildUser(Set.of(updatedRoleReference));
        userChanges.setInstitutionalEmail("new.user@icesi.edu.co");
        userChanges.setPasswordHash("newHash");
        userChanges.setFullName("New User");
        userChanges.setProfilePhotoUrl("http://new-photo");
        userChanges.setCreatedAt(LocalDateTime.now().plusDays(1));

        when(userAccountRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(updatedRole.getId())).thenReturn(Optional.of(updatedRole));
        when(userAccountRepository.save(any(UserAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserAccount updatedUser = userService.updateUser(existingUser.getId(), userChanges);

        assertEquals(userChanges.getInstitutionalEmail(), updatedUser.getInstitutionalEmail());
        assertEquals(userChanges.getPasswordHash(), updatedUser.getPasswordHash());
        assertEquals(userChanges.getFullName(), updatedUser.getFullName());
        assertEquals(userChanges.getProfilePhotoUrl(), updatedUser.getProfilePhotoUrl());
        assertEquals(userChanges.getCreatedAt(), updatedUser.getCreatedAt());
        assertEquals(Set.of(updatedRole), updatedUser.getRoles());
        verify(userAccountRepository).save(existingUser);
    }

    @Test
    void updateUser_withoutRoles_throwsException() {
        Role initialRole = buildRole(1, "Teacher");
        UserAccount existingUser = buildUser(Set.of(initialRole));
        existingUser.setId(10);

        when(userAccountRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        UserAccount userChanges = buildUser(Collections.emptySet());

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(existingUser.getId(), userChanges));
        verify(userAccountRepository, never()).save(any(UserAccount.class));
    }

    @Test
    void updateUser_withUnknownRole_throwsException() {
        Role initialRole = buildRole(1, "Teacher");
        UserAccount existingUser = buildUser(Set.of(initialRole));
        existingUser.setId(8);

        when(userAccountRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        Role unknownRole = new Role();
        unknownRole.setId(99);
        UserAccount userChanges = buildUser(Set.of(unknownRole));

        when(roleRepository.findById(unknownRole.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(existingUser.getId(), userChanges));
        verify(userAccountRepository, never()).save(any(UserAccount.class));
    }

    @Test
    void updateUser_whenNotFound_throwsException() {
        UserAccount userChanges = buildUser(Set.of(buildRole(1, "Teacher")));

        when(userAccountRepository.findById(123)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(123, userChanges));
        verify(userAccountRepository, never()).save(any(UserAccount.class));
    }

    @Test
    void deleteUser_existingUser_deletesSuccessfully() {
        UserAccount existingUser = buildUser(Set.of(buildRole(1, "Teacher")));
        existingUser.setId(3);

        when(userAccountRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        userService.deleteUser(existingUser.getId());

        verify(userAccountRepository).delete(existingUser);
    }

    @Test
    void deleteUser_whenNotFound_throwsException() {
        when(userAccountRepository.findById(77)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(77));
        verify(userAccountRepository, never()).delete(any(UserAccount.class));
    }

    @Test
    void getUserById_returnsStoredUser() {
        UserAccount existingUser = buildUser(Set.of(buildRole(1, "Teacher")));
        existingUser.setId(4);

        when(userAccountRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        UserAccount result = userService.getUserById(existingUser.getId());

        assertNotNull(result);
        assertEquals(existingUser.getId(), result.getId());
    }

    @Test
    void getUserById_whenNotFound_throwsException() {
        when(userAccountRepository.findById(55)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(55));
    }

    @Test
    void getAllUsers_delegatesToRepository() {
        List<UserAccount> users = List.of(buildUser(Set.of(buildRole(1, "Teacher"))));
        when(userAccountRepository.findAll()).thenReturn(users);

        List<UserAccount> result = userService.getAllUsers();

        assertEquals(users, result);
    }

    private UserAccount buildUser(Set<Role> roles) {
        UserAccount user = new UserAccount();
        user.setInstitutionalEmail("user@icesi.edu.co");
        user.setPasswordHash("secureHash");
        user.setFullName("User Test");
        user.setCreatedAt(LocalDateTime.now());
        if (roles != null) {
            user.setRoles(roles);
        }
        return user;
    }

    private Role buildRole(Integer id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }
}
