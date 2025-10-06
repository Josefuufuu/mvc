package edu.icesi.pensamiento_computacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.Exercise;
import edu.icesi.pensamiento_computacional.model.LevelTierCode;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    List<Exercise> findByDifficulty(Integer difficulty);

    List<Exercise> findByTargetProfiles_Profile_Code(LevelTierCode profileCode);
}
