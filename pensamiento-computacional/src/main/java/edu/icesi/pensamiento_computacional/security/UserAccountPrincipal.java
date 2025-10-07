package edu.icesi.pensamiento_computacional.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import edu.icesi.pensamiento_computacional.model.UserAccount;

public class UserAccountPrincipal extends User {

    private final Integer id;
    private final String fullName;

    public UserAccountPrincipal(UserAccount userAccount, Collection<? extends GrantedAuthority> authorities) {
        super(userAccount.getInstitutionalEmail(), userAccount.getPasswordHash(), authorities);
        this.id = userAccount.getId();
        this.fullName = userAccount.getFullName();
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
