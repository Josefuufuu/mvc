package edu.icesi.pensamiento_computacional.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.pensamiento_computacional.model.Enrollment;
import edu.icesi.pensamiento_computacional.model.EnrollmentId;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    List<Enrollment> findByAcademicTerm_Id(Integer academicTermId);

    List<Enrollment> findByStudent_Id(Integer studentId);

    List<Enrollment> findByGroupSection_Id(Integer groupSectionId);
}
