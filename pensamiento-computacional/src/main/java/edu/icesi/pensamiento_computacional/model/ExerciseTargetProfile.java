package edu.icesi.pensamiento_computacional.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(
    name = "EXERCISE_TARGET_PROFILE"
    
)
public class ExerciseTargetProfile {

    @EmbeddedId
    private ExerciseTargetProfileId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("exerciseId")
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("profileCode")
    @JoinColumn(name = "profile_code", nullable = false)
    private LevelTier profile;

}
