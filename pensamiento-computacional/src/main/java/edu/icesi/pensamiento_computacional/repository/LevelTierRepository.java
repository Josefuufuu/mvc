package edu.icesi.pensamiento_computacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.LevelTier;
import edu.icesi.pensamiento_computacional.model.LevelTierCode;

@Repository
public interface LevelTierRepository extends JpaRepository<LevelTier, LevelTierCode> {

    List<LevelTier> findByPerformanceTierHistories_AcademicTerm_Id(Integer academicTermId);
}
