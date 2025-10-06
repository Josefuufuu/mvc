package edu.icesi.pensamiento_computacional.services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.icesi.pensamiento_computacional.model.Enrollment;
import edu.icesi.pensamiento_computacional.repository.EnrollmentRepository;
import edu.icesi.pensamiento_computacional.services.IEnrollmentService;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Enrollment> findByTerm(Integer termId) {
        return enrollmentRepository.findByAcademicTerm_Id(termId);
    }
}
