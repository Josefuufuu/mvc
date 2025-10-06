package edu.icesi.pensamiento_computacional.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import edu.icesi.pensamiento_computacional.services.IEnrollmentService;

@SpringBootTest
@ActiveProfiles("test")
public class EnrollmentRepositoryTest {


    @Autowired
    private IEnrollmentService enrollmentService;


    //plant data
    @Test
    void findByTerm(){
        enrollmentService.findByTerm(11);
    }

    
}
