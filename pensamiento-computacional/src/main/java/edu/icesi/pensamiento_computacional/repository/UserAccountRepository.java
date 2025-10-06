package edu.icesi.pensamiento_computacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.LevelTierCode;
import edu.icesi.pensamiento_computacional.model.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    Optional<UserAccount> findByInstitutionalEmail(String institutionalEmail);

    List<UserAccount> findByRoles_Name(String roleName);

    List<UserAccount> findBySelfDeclaredLevel_Code(LevelTierCode levelTierCode);
}
