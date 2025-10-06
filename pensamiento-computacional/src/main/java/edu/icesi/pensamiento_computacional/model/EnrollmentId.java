package edu.icesi.pensamiento_computacional.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EnrollmentId implements Serializable {



    @Column(name = "academic_term_id")
    private Integer academicTermId;

    @Column(name = "student_id")
    private Integer studentId;




}
