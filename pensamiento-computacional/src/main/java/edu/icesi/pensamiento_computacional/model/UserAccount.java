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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "USER_ACCOUNT", uniqueConstraints = {@UniqueConstraint(name="uq_user_account_email", columnNames="institutional_email")})

public class UserAccount {
    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;

    @Column(name="institutional_email", nullable=false, length=255)
    private String institutionalEmail;

    @Column(name="password_hash", nullable=false, length=255)
    private String passwordHash;

    @Column(name="full_name", nullable=false, length=255)
    private String fullName;

    @Column(name="profile_photo_url", nullable=true)
    private String profilePhotoUrl;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @ManyToOne(optional=true, fetch=FetchType.LAZY)
    @JoinColumn(name="self_declared_level")
    private LevelTier selfDeclaredLevel;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "USER_ACCOUNT_ROLE",
        joinColumns = @JoinColumn(name = "user_account_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Activity> createdActivities = new HashSet<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<ScoringEvent> scoringEventsAsStudent = new HashSet<>();

    @OneToMany(mappedBy = "awardedBy", fetch = FetchType.LAZY)
    private Set<ScoringEvent> scoringEventsAwarded = new HashSet<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<PerformanceTierHistory> performanceTierHistories = new HashSet<>();

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private Set<TeachingAssignment> teachingAssignments = new HashSet<>();
}

