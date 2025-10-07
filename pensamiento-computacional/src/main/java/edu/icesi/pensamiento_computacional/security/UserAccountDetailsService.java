package edu.icesi.pensamiento_computacional.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.model.UserAccount;
import edu.icesi.pensamiento_computacional.repository.UserAccountRepository;

@Service
public class UserAccountDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByInstitutionalEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No se encontró un usuario con el correo institucional proporcionado."));

        Set<String> authorities = userAccount.getRoles().stream()
                .map(Role::getName)
                .map(roleName -> "ROLE_" + roleName.toUpperCase())
                .collect(Collectors.toSet());

        return User.builder()
                .username(userAccount.getInstitutionalEmail())
                .password(userAccount.getPasswordHash())
                .authorities(authorities.toArray(String[]::new))
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
