package edu.icesi.pensamiento_computacional.model;




import java.sql.Timestamp;
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
@Table(name = "ACTIVITY")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_section_id", nullable = false)
    private GroupSection groupSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private UserAccount createdBy;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "window_start_at", nullable = false)
    private Timestamp windowStartAt;

    @Column(name = "window_end_at")
    private Timestamp windowEndAt;

    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private Set<ActivityExercise> activityExercises = new HashSet<>();




    
}
