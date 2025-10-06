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
public class ActivityExerciseId implements Serializable {




    @Column(name = "exercise_id")
    private Integer exerciseId;

    @Column(name = "activity_id")
    private Integer activityId;




}
