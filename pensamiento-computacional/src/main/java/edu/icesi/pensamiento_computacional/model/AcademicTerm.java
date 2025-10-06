package edu.icesi.pensamiento_computacional.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name="ACADEMIC_TERM", 
        uniqueConstraints={
            @UniqueConstraint(name="unq_term_code", columnNames="term_code" )
        })
public class AcademicTerm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="term_code", nullable=false, length=30)
    private String termCode;

    @Column(name="start_on", nullable=false)
    private LocalDateTime startOn;

    @Column(name = "ends_on", nullable=false)
    private LocalDateTime endsOn;

    @Column(name="is_active", nullable=false)
    private Boolean isActive;

    @PrePersist
    @PreUpdate
    private void validateDates(){
        if(startOn != null && endsOn !=null && startOn.isAfter(endsOn)){
            throw new IllegalStateException("Start on must be <= ends on");
        }
    }

    @OneToMany(mappedBy = "academicTerm", fetch = FetchType.LAZY)
    private Set<PerformanceTierHistory> performanceTierHistories = new HashSet<>();

    @OneToMany(mappedBy = "academicTerm", fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "academicTerm", fetch = FetchType.LAZY)
    private Set<GroupSection> groupSections = new HashSet<>();


}
