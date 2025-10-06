package edu.icesi.pensamiento_computacional.model;



import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
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
    name = "ACTIVITY_EXERCISE",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_activity_display", columnNames = {"activity_id", "display_order"})
    }
)
public class ActivityExercise {


    @EmbeddedId
    private ActivityExerciseId id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("activityId")
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("exerciseId")
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @OneToMany(mappedBy = "activityExercise", fetch = FetchType.LAZY)
    private Set<ScoringEvent> scoringEvents = new HashSet<>();



}
