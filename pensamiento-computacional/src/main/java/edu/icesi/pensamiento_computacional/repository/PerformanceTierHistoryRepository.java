package edu.icesi.pensamiento_computacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.PerformanceTierHistory;

@Repository
public interface PerformanceTierHistoryRepository extends JpaRepository<PerformanceTierHistory, Integer> {

    List<PerformanceTierHistory> findByStudent_IdOrderByComputedAtDesc(Integer studentId);

    List<PerformanceTierHistory> findByAcademicTerm_Id(Integer academicTermId);

    Optional<PerformanceTierHistory> findFirstByStudent_IdAndAcademicTerm_IdOrderByRevisionNoDesc(Integer studentId, Integer academicTermId);
}
