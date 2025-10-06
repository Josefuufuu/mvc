package edu.icesi.pensamiento_computacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.ExerciseTargetProfile;
import edu.icesi.pensamiento_computacional.model.ExerciseTargetProfileId;
import edu.icesi.pensamiento_computacional.model.LevelTierCode;

@Repository
public interface ExerciseTargetProfileRepository extends JpaRepository<ExerciseTargetProfile, ExerciseTargetProfileId> {

    List<ExerciseTargetProfile> findByProfile_Code(LevelTierCode profileCode);

    List<ExerciseTargetProfile> findByExercise_Id(Integer exerciseId);
}
