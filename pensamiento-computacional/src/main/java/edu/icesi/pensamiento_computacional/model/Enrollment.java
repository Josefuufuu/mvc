package edu.icesi.pensamiento_computacional.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name="ENROLLMENT", uniqueConstraints = @UniqueConstraint(name="uq_enrollment_student_term",columnNames={"student_id","academic_term_id"}))
public class Enrollment {

  @EmbeddedId
  private EnrollmentId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("studentId")
  @JoinColumn(name = "student_id", nullable = false)
  private UserAccount student;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("academicTermId")
  @JoinColumn(name = "academic_term_id", nullable = false)
  private AcademicTerm academicTerm;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_section_id", nullable = false)
  private GroupSection groupSection;

  @Column(name="enrolled_on")
  private LocalDateTime enrolledOn;
    

}



