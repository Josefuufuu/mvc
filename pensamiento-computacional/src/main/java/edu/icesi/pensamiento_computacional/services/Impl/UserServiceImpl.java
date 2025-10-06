package edu.icesi.pensamiento_computacional.services.Impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.model.UserAccount;
import edu.icesi.pensamiento_computacional.repository.RoleRepository;
import edu.icesi.pensamiento_computacional.repository.UserAccountRepository;
import edu.icesi.pensamiento_computacional.services.UserService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserAccountRepository userAccountRepository, RoleRepository roleRepository) {
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserAccount createUser(UserAccount user) {
        Set<Role> managedRoles = loadRoles(user.getRoles());
        user.setRoles(managedRoles);
        return userAccountRepository.save(user);
    }

    @Override
    public UserAccount updateUser(Integer id, UserAccount user) {
        UserAccount existingUser = userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User account not found with id " + id));

        existingUser.setInstitutionalEmail(user.getInstitutionalEmail());
        existingUser.setPasswordHash(user.getPasswordHash());
        existingUser.setFullName(user.getFullName());
        existingUser.setProfilePhotoUrl(user.getProfilePhotoUrl());
        existingUser.setCreatedAt(user.getCreatedAt());
        existingUser.setSelfDeclaredLevel(user.getSelfDeclaredLevel());
        existingUser.setRoles(loadRoles(user.getRoles()));

        return userAccountRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Integer id) {
        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User account not found with id " + id));
        userAccountRepository.delete(user);
    }

    @Override
    public UserAccount getUserById(Integer id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User account not found with id " + id));
    }

    @Override
    public List<UserAccount> getAllUsers() {
        return userAccountRepository.findAll();
    }

    private Set<Role> loadRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("A user must have at least one role assigned.");
        }

        return roles.stream().map(role -> {
            Integer roleId = role.getId();
            if (roleId == null) {
                throw new IllegalArgumentException(
                        "Role identifiers must be provided when assigning them to a user account.");
            }
            return roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + roleId));
        }).collect(Collectors.toSet());
    }
}
