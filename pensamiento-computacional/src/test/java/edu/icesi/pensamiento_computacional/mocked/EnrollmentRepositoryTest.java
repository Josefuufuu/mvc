package edu.icesi.pensamiento_computacional.mocked;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import edu.icesi.pensamiento_computacional.model.Enrollment;
import edu.icesi.pensamiento_computacional.model.EnrollmentId;
import edu.icesi.pensamiento_computacional.repository.EnrollmentRepository;
import edu.icesi.pensamiento_computacional.services.IEnrollmentService;

@SpringBootTest
@ActiveProfiles("test")
public class EnrollmentRepositoryTest {

   @Autowired
   private IEnrollmentService enrollmentService;

   @MockBean
   private EnrollmentRepository enrollmentRepository;

        @Test
        void contextLoads() {
        }


   @Test
   void findByTermTest(){
    Integer termId = 123;
    Integer studentId = 1;
    Enrollment e = new Enrollment();
    e.setId(new EnrollmentId(termId, studentId));
    e.setEnrolledOn(LocalDateTime.now());

    when(enrollmentRepository.findByAcademicTerm_Id(termId)).thenReturn(List.of(e));

    var result = enrollmentService.findByTerm(termId);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(termId, result.get(0).getId().getAcademicTermId());
    assertEquals(studentId, result.get(0).getId().getStudentId());

    verify(enrollmentRepository, times(1)).findByAcademicTerm_Id(termId);
    verifyNoMoreInteractions(enrollmentRepository);


   }
}
