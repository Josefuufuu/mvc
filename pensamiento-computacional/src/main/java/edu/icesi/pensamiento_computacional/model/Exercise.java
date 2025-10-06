package edu.icesi.pensamiento_computacional.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "EXERCISE")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 4000)
    private String prompt;

    @Column(nullable = false)
    private Integer difficulty;

    @OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY)
    private List<ExerciseTargetProfile> targetProfiles = new ArrayList<>();

    @OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY)
    private Set<ActivityExercise> activityExercises = new HashSet<>();

}
