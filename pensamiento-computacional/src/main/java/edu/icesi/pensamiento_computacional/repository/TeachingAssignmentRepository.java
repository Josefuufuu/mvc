package edu.icesi.pensamiento_computacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.TeachingAssignment;

@Repository
public interface TeachingAssignmentRepository extends JpaRepository<TeachingAssignment, Integer> {

    List<TeachingAssignment> findByProfessor_Id(Integer professorId);

    List<TeachingAssignment> findByGroupSection_Id(Integer groupSectionId);
}
