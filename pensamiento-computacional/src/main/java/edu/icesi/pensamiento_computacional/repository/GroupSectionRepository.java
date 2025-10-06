package edu.icesi.pensamiento_computacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.GroupSection;

@Repository
public interface GroupSectionRepository extends JpaRepository<GroupSection, Integer> {

    List<GroupSection> findByAcademicTerm_Id(Integer academicTermId);

    Optional<GroupSection> findByAcademicTerm_IdAndGroupCode(Integer academicTermId, String groupCode);
}
