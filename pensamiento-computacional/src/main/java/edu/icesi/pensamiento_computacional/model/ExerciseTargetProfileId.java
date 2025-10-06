package edu.icesi.pensamiento_computacional.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ExerciseTargetProfileId implements Serializable {

    @Column(name = "exercise_id")
    private Integer exerciseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_code")
    private LevelTierCode profileCode;

}
