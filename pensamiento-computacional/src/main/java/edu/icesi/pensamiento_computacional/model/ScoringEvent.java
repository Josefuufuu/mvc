package edu.icesi.pensamiento_computacional.model;



import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
    name = "SCORING_EVENT"
)

public class ScoringEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",nullable = false)
    private UserAccount student;

    @ManyToOne(targetEntity =  ActivityExercise.class, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn (name = "activity_id"),
        @JoinColumn(name = "exercise_id")
    })
    private ActivityExercise activityExercise; //Cuando voy a pegar una columna con pk en 2, pego las 2 columnas con un @JoinColumns


    @Column(name = "awarded_points",nullable = false)
    private Integer awardedPoints;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "awarded_by_user_id",nullable = false)
    private UserAccount awardedBy;

    


    
}
