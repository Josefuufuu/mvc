package edu.icesi.pensamiento_computacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);

    List<Role> findByPermissions_Name(String permissionName);
}
