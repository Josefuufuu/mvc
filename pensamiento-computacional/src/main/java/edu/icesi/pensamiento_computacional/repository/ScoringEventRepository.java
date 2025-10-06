package edu.icesi.pensamiento_computacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.ScoringEvent;

@Repository
public interface ScoringEventRepository extends JpaRepository<ScoringEvent, Integer> {

    List<ScoringEvent> findByStudent_Id(Integer studentId);

    List<ScoringEvent> findByAwardedBy_Id(Integer awardedById);

    List<ScoringEvent> findByActivityExercise_Activity_Id(Integer activityId);
}
