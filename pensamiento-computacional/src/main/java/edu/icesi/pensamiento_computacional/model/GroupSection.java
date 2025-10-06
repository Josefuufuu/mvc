package edu.icesi.pensamiento_computacional.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(
    name = "GROUP_SECTION",
    uniqueConstraints = {
        @UniqueConstraint(name = "un_group_code_per_term", columnNames = {"academic_term_id", "group_code"}),
        @UniqueConstraint(name = "un_group_term_id_id", columnNames = {"academic_term_id", "id"})
    }
)
public class GroupSection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="academic_term_id", nullable=false)
    private AcademicTerm academicTerm;

    @Column(name = "group_code", nullable=false,length=20)
    private String groupCode;

    @Column(name = "section_title", length=255)
    private String sectionTitle;

    @OneToMany(mappedBy = "groupSection", fetch = FetchType.LAZY)
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "groupSection", fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments = new HashSet<>();

}
