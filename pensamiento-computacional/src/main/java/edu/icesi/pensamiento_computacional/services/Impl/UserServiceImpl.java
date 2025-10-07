package edu.icesi.pensamiento_computacional.services.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserAccountRepository userAccountRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserAccount createUser(UserAccount user) {
        if (user == null) {
            throw new IllegalArgumentException("User information is required to create an account.");
        }

        Set<Role> managedRoles = loadRoles(user.getRoles());

        if (user.getInstitutionalEmail() == null || user.getInstitutionalEmail().isBlank()) {
            throw new IllegalArgumentException("El correo institucional es obligatorio.");
        }

        if (userAccountRepository.existsByInstitutionalEmail(user.getInstitutionalEmail())) {
            throw new IllegalArgumentException("El correo institucional ya está registrado.");
        }

        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        user.setRoles(managedRoles);
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userAccountRepository.save(user);
    }

    @Override
    @Transactional
    public UserAccount updateUser(Integer id, UserAccount user) {
        UserAccount existingUser = userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User account not found with id " + id));

        existingUser.setInstitutionalEmail(user.getInstitutionalEmail());
        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        existingUser.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        existingUser.setFullName(user.getFullName());
        existingUser.setProfilePhotoUrl(user.getProfilePhotoUrl());
        existingUser.setCreatedAt(user.getCreatedAt());
        existingUser.setSelfDeclaredLevel(user.getSelfDeclaredLevel());
        existingUser.setRoles(loadRoles(user.getRoles()));

        return userAccountRepository.save(existingUser);
    }

    @Override
    @Transactional
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

    @Override
    public UserAccount authenticate(String institutionalEmail, String password) {
        return userAccountRepository.findByInstitutionalEmail(institutionalEmail)
                .filter(user -> password != null && passwordEncoder.matches(password, user.getPasswordHash()))
                .orElseThrow(() -> new IllegalArgumentException("Correo o contraseña incorrectos."));
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
