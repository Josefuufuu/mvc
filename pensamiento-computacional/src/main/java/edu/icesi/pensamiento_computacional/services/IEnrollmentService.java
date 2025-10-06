package edu.icesi.pensamiento_computacional.services;

import java.util.List;

import edu.icesi.pensamiento_computacional.model.Enrollment;

public interface IEnrollmentService {

    List<Enrollment> findByTerm(Integer termId);
}
