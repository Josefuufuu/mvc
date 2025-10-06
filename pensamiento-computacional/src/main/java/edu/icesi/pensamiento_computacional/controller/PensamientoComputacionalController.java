package edu.icesi.pensamiento_computacional.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.icesi.pensamiento_computacional.model.AcademicTerm;
import edu.icesi.pensamiento_computacional.model.Activity;
import edu.icesi.pensamiento_computacional.model.ActivityExercise;
import edu.icesi.pensamiento_computacional.model.Enrollment;
import edu.icesi.pensamiento_computacional.model.Exercise;
import edu.icesi.pensamiento_computacional.model.ExerciseTargetProfile;
import edu.icesi.pensamiento_computacional.model.GroupSection;
import edu.icesi.pensamiento_computacional.model.LevelTier;
import edu.icesi.pensamiento_computacional.model.LevelTierCode;
import edu.icesi.pensamiento_computacional.model.PerformanceTierHistory;
import edu.icesi.pensamiento_computacional.model.Permission;
import edu.icesi.pensamiento_computacional.model.Role;
import edu.icesi.pensamiento_computacional.model.ScoringEvent;
import edu.icesi.pensamiento_computacional.model.TeachingAssignment;
import edu.icesi.pensamiento_computacional.model.UserAccount;
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

@RestController
@RequestMapping
public class PensamientoComputacionalController {

    private final AcademicTermRepository academicTermRepository;
    private final ActivityRepository activityRepository;
    private final ActivityExerciseRepository activityExerciseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseTargetProfileRepository exerciseTargetProfileRepository;
    private final GroupSectionRepository groupSectionRepository;
    private final LevelTierRepository levelTierRepository;
    private final PerformanceTierHistoryRepository performanceTierHistoryRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final ScoringEventRepository scoringEventRepository;
    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final UserAccountRepository userAccountRepository;

    public PensamientoComputacionalController(
            AcademicTermRepository academicTermRepository,
            ActivityRepository activityRepository,
            ActivityExerciseRepository activityExerciseRepository,
            EnrollmentRepository enrollmentRepository,
            ExerciseRepository exerciseRepository,
            ExerciseTargetProfileRepository exerciseTargetProfileRepository,
            GroupSectionRepository groupSectionRepository,
            LevelTierRepository levelTierRepository,
            PerformanceTierHistoryRepository performanceTierHistoryRepository,
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            ScoringEventRepository scoringEventRepository,
            TeachingAssignmentRepository teachingAssignmentRepository,
            UserAccountRepository userAccountRepository) {
        this.academicTermRepository = academicTermRepository;
        this.activityRepository = activityRepository;
        this.activityExerciseRepository = activityExerciseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseTargetProfileRepository = exerciseTargetProfileRepository;
        this.groupSectionRepository = groupSectionRepository;
        this.levelTierRepository = levelTierRepository;
        this.performanceTierHistoryRepository = performanceTierHistoryRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.scoringEventRepository = scoringEventRepository;
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @GetMapping("/academic-terms/by-code/{termCode}")
    public ResponseEntity<AcademicTerm> getAcademicTermByCode(@PathVariable String termCode) {
        return ResponseEntity.of(academicTermRepository.findByTermCode(termCode));
    }

    @GetMapping("/academic-terms/active")
    public List<AcademicTerm> getActiveAcademicTerms() {
        return academicTermRepository.findByIsActiveTrue();
    }

    @GetMapping("/activities/by-group-section")
    public List<Activity> getActivitiesByGroupSection(@RequestParam Integer groupSectionId) {
        return activityRepository.findByGroupSection_Id(groupSectionId);
    }

    @GetMapping("/activities/by-created-by")
    public List<Activity> getActivitiesByCreatedBy(@RequestParam Integer userId) {
        return activityRepository.findByCreatedBy_Id(userId);
    }

    @GetMapping("/activities/by-academic-term")
    public List<Activity> getActivitiesByAcademicTerm(@RequestParam Integer academicTermId) {
        return activityRepository.findByGroupSection_AcademicTerm_Id(academicTermId);
    }

    @GetMapping("/activity-exercises/by-activity")
    public List<ActivityExercise> getActivityExercisesByActivity(@RequestParam Integer activityId) {
        return activityExerciseRepository.findByActivity_IdOrderByDisplayOrderAsc(activityId);
    }

    @GetMapping("/activity-exercises/by-exercise")
    public List<ActivityExercise> getActivityExercisesByExercise(@RequestParam Integer exerciseId) {
        return activityExerciseRepository.findByExercise_Id(exerciseId);
    }

    @GetMapping("/activity-exercises/by-activity-and-exercise")
    public ResponseEntity<ActivityExercise> getActivityExercisesByActivityAndExercise(
            @RequestParam Integer activityId,
            @RequestParam Integer exerciseId) {
        return ResponseEntity.of(
                activityExerciseRepository.findByActivity_IdAndExercise_Id(activityId, exerciseId));
    }

    @GetMapping("/enrollments/by-academic-term")
    public List<Enrollment> getEnrollmentsByAcademicTerm(@RequestParam Integer academicTermId) {
        return enrollmentRepository.findByAcademicTerm_Id(academicTermId);
    }

    @GetMapping("/enrollments/by-student")
    public List<Enrollment> getEnrollmentsByStudent(@RequestParam Integer studentId) {
        return enrollmentRepository.findByStudent_Id(studentId);
    }

    @GetMapping("/enrollments/by-group-section")
    public List<Enrollment> getEnrollmentsByGroupSection(@RequestParam Integer groupSectionId) {
        return enrollmentRepository.findByGroupSection_Id(groupSectionId);
    }

    @GetMapping("/exercises/by-difficulty")
    public List<Exercise> getExercisesByDifficulty(@RequestParam Integer difficulty) {
        return exerciseRepository.findByDifficulty(difficulty);
    }

    @GetMapping("/exercises/by-target-profile")
    public List<Exercise> getExercisesByTargetProfile(@RequestParam LevelTierCode profileCode) {
        return exerciseRepository.findByTargetProfiles_Profile_Code(profileCode);
    }

    @GetMapping("/exercise-target-profiles/by-profile")
    public List<ExerciseTargetProfile> getExerciseTargetProfilesByProfile(@RequestParam LevelTierCode profileCode) {
        return exerciseTargetProfileRepository.findByProfile_Code(profileCode);
    }

    @GetMapping("/exercise-target-profiles/by-exercise")
    public List<ExerciseTargetProfile> getExerciseTargetProfilesByExercise(@RequestParam Integer exerciseId) {
        return exerciseTargetProfileRepository.findByExercise_Id(exerciseId);
    }

    @GetMapping("/group-sections/by-academic-term")
    public List<GroupSection> getGroupSectionsByAcademicTerm(@RequestParam Integer academicTermId) {
        return groupSectionRepository.findByAcademicTerm_Id(academicTermId);
    }

    @GetMapping("/group-sections/by-academic-term-and-code")
    public ResponseEntity<GroupSection> getGroupSectionsByAcademicTermAndCode(
            @RequestParam Integer academicTermId,
            @RequestParam String groupCode) {
        return ResponseEntity.of(
                groupSectionRepository.findByAcademicTerm_IdAndGroupCode(academicTermId, groupCode));
    }

    @GetMapping("/level-tiers/by-academic-term")
    public List<LevelTier> getLevelTiersByAcademicTerm(@RequestParam Integer academicTermId) {
        return levelTierRepository.findByPerformanceTierHistories_AcademicTerm_Id(academicTermId);
    }

    @GetMapping("/performance-tier-histories/by-student")
    public List<PerformanceTierHistory> getPerformanceTierHistoriesByStudent(@RequestParam Integer studentId) {
        return performanceTierHistoryRepository.findByStudent_IdOrderByComputedAtDesc(studentId);
    }

    @GetMapping("/performance-tier-histories/by-academic-term")
    public List<PerformanceTierHistory> getPerformanceTierHistoriesByAcademicTerm(
            @RequestParam Integer academicTermId) {
        return performanceTierHistoryRepository.findByAcademicTerm_Id(academicTermId);
    }

    @GetMapping("/performance-tier-histories/latest-by-student-and-academic-term")
    public ResponseEntity<PerformanceTierHistory> getLatestPerformanceTierHistoryByStudentAndAcademicTerm(
            @RequestParam Integer studentId,
            @RequestParam Integer academicTermId) {
        return ResponseEntity.of(performanceTierHistoryRepository
                .findFirstByStudent_IdAndAcademicTerm_IdOrderByRevisionNoDesc(studentId, academicTermId));
    }

    @GetMapping("/permissions/by-name/{name}")
    public ResponseEntity<Permission> getPermissionByName(@PathVariable String name) {
        return ResponseEntity.of(permissionRepository.findByName(name));
    }

    @GetMapping("/permissions/by-role")
    public List<Permission> getPermissionsByRole(@RequestParam Integer roleId) {
        return permissionRepository.findByRoles_Id(roleId);
    }

    @GetMapping("/roles/by-name/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {
        return ResponseEntity.of(roleRepository.findByName(name));
    }

    @GetMapping("/roles/by-permission")
    public List<Role> getRolesByPermission(@RequestParam String permissionName) {
        return roleRepository.findByPermissions_Name(permissionName);
    }

    @GetMapping("/scoring-events/by-student")
    public List<ScoringEvent> getScoringEventsByStudent(@RequestParam Integer studentId) {
        return scoringEventRepository.findByStudent_Id(studentId);
    }

    @GetMapping("/scoring-events/by-awarded-by")
    public List<ScoringEvent> getScoringEventsByAwardedBy(@RequestParam Integer awardedById) {
        return scoringEventRepository.findByAwardedBy_Id(awardedById);
    }

    @GetMapping("/scoring-events/by-activity")
    public List<ScoringEvent> getScoringEventsByActivity(@RequestParam Integer activityId) {
        return scoringEventRepository.findByActivityExercise_Activity_Id(activityId);
    }

    @GetMapping("/teaching-assignments/by-professor")
    public List<TeachingAssignment> getTeachingAssignmentsByProfessor(@RequestParam Integer professorId) {
        return teachingAssignmentRepository.findByProfessor_Id(professorId);
    }

    @GetMapping("/teaching-assignments/by-group-section")
    public List<TeachingAssignment> getTeachingAssignmentsByGroupSection(@RequestParam Integer groupSectionId) {
        return teachingAssignmentRepository.findByGroupSection_Id(groupSectionId);
    }

    @GetMapping("/users/by-email/{institutionalEmail}")
    public ResponseEntity<UserAccount> getUserByEmail(@PathVariable String institutionalEmail) {
        return ResponseEntity.of(userAccountRepository.findByInstitutionalEmail(institutionalEmail));
    }

    @GetMapping("/users/by-role")
    public List<UserAccount> getUsersByRole(@RequestParam String roleName) {
        return userAccountRepository.findByRoles_Name(roleName);
    }

    @GetMapping("/users/by-self-declared-level")
    public List<UserAccount> getUsersBySelfDeclaredLevel(@RequestParam LevelTierCode levelCode) {
        return userAccountRepository.findBySelfDeclaredLevel_Code(levelCode);
    }
}
