package edu.icesi.pensamiento_computacional.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;





@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(
    name = "PERFORMANCE_TIER_HISTORY",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_pth_revison", columnNames = {"student_id", "academic_term_id","revision_no"})
    }
)
public class PerformanceTierHistory {


    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="student_id", nullable=false)
    private UserAccount student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="academic_term_id", nullable=false)
    private AcademicTerm academicTerm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="tier_code", nullable=false)
    private LevelTier levelTier;

    private Timestamp computedAt ;
    
    @Column(name = "revision_no")
    private Integer revisionNo;
    @Column(name = "basis_summary")
    private String basisSummary;
    @Column(name = "method_version")
    private String methodVersion;


    


}
