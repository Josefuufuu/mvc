package edu.icesi.pensamiento_computacional.security;

import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
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

        Set<GrantedAuthority> authorities = userAccount.getRoles().stream()
                .map(Role::getName)
                .map(roleName -> "ROLE_" + roleName.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UserAccountPrincipal(userAccount, authorities);
    }
}
