package edu.icesi.pensamiento_computacional.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.ActivityExercise;
import edu.icesi.pensamiento_computacional.model.ActivityExerciseId;

@Repository
public interface ActivityExerciseRepository extends JpaRepository<ActivityExercise, ActivityExerciseId> {

    List<ActivityExercise> findByActivity_IdOrderByDisplayOrderAsc(Integer activityId);

    List<ActivityExercise> findByExercise_Id(Integer exerciseId);

    Optional<ActivityExercise> findByActivity_IdAndExercise_Id(Integer activityId, Integer exerciseId);
}
