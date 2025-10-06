package edu.icesi.pensamiento_computacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByGroupSection_Id(Integer groupSectionId);

    List<Activity> findByCreatedBy_Id(Integer userId);

    List<Activity> findByGroupSection_AcademicTerm_Id(Integer academicTermId);
}
