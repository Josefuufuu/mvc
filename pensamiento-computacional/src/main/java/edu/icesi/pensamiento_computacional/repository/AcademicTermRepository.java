package edu.icesi.pensamiento_computacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.AcademicTerm;

@Repository
public interface AcademicTermRepository extends JpaRepository<AcademicTerm, Integer> {

    Optional<AcademicTerm> findByTermCode(String termCode);

    List<AcademicTerm> findByIsActiveTrue();
}
