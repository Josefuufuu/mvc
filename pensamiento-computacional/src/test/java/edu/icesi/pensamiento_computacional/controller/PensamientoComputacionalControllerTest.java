package edu.icesi.pensamiento_computacional.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.icesi.pensamiento_computacional.model.AcademicTerm;
import edu.icesi.pensamiento_computacional.model.Permission;
import edu.icesi.pensamiento_computacional.repository.AcademicTermRepository;
import edu.icesi.pensamiento_computacional.repository.ActivityExerciseRepository;
import edu.icesi.pensamiento_computacional.repository.ActivityRepository;
import edu.icesi.pensamiento_computacional.repository.EnrollmentRepository;
import edu.icesi.pensamiento_computacional.repository.ExerciseRepository;
import edu.icesi.pensamiento_computacional.repository.ExerciseTargetProfileRepository;
import edu.icesi.pensamiento_computacional.repository.GroupSectionRepository;
import edu.icesi.pensamiento_computacional.repository.LevelTierRepository;
import edu.icesi.pensamiento_computacional.repository.PerformanceTierHistoryRepository;
import edu.icesi.pensamiento_computacional.repository.PermissionRepository;
import edu.icesi.pensamiento_computacional.repository.RoleRepository;
import edu.icesi.pensamiento_computacional.repository.ScoringEventRepository;
import edu.icesi.pensamiento_computacional.repository.TeachingAssignmentRepository;
import edu.icesi.pensamiento_computacional.repository.UserAccountRepository;

@WebMvcTest(PensamientoComputacionalController.class)
class PensamientoComputacionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcademicTermRepository academicTermRepository;

    @MockBean
    private ActivityRepository activityRepository;

    @MockBean
    private ActivityExerciseRepository activityExerciseRepository;

    @MockBean
    private EnrollmentRepository enrollmentRepository;

    @MockBean
    private ExerciseRepository exerciseRepository;

    @MockBean
    private ExerciseTargetProfileRepository exerciseTargetProfileRepository;

    @MockBean
    private GroupSectionRepository groupSectionRepository;

    @MockBean
    private LevelTierRepository levelTierRepository;

    @MockBean
    private PerformanceTierHistoryRepository performanceTierHistoryRepository;

    @MockBean
    private PermissionRepository permissionRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private ScoringEventRepository scoringEventRepository;

    @MockBean
    private TeachingAssignmentRepository teachingAssignmentRepository;

    @MockBean
    private UserAccountRepository userAccountRepository;

    @Test
    void getAcademicTermByCodeReturnsAcademicTermWhenPresent() throws Exception {
        AcademicTerm academicTerm = new AcademicTerm();
        academicTerm.setId(1);
        academicTerm.setTermCode("2023A");
        academicTerm.setStartOn(LocalDateTime.of(2023, 1, 1, 0, 0));
        academicTerm.setEndsOn(LocalDateTime.of(2023, 6, 30, 0, 0));
        academicTerm.setIsActive(true);

        when(academicTermRepository.findByTermCode("2023A")).thenReturn(Optional.of(academicTerm));

        mockMvc.perform(get("/academic-terms/by-code/2023A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.termCode").value("2023A"));
    }

    @Test
    void getAcademicTermByCodeReturnsNotFoundWhenAbsent() throws Exception {
        when(academicTermRepository.findByTermCode("2023B")).thenReturn(Optional.empty());

        mockMvc.perform(get("/academic-terms/by-code/2023B"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPermissionByNameReturnsPermissionWhenPresent() throws Exception {
        Permission permission = new Permission();
        permission.setId(1);
        permission.setName("READ_ACTIVITIES");
        permission.setDescription("Read activities");

        when(permissionRepository.findByName("READ_ACTIVITIES")).thenReturn(Optional.of(permission));

        mockMvc.perform(get("/permissions/by-name/READ_ACTIVITIES"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("READ_ACTIVITIES"));
    }

    @Test
    void getPermissionByNameReturnsNotFoundWhenAbsent() throws Exception {
        when(permissionRepository.findByName("MISSING")).thenReturn(Optional.empty());

        mockMvc.perform(get("/permissions/by-name/MISSING"))
                .andExpect(status().isNotFound());
    }
}
