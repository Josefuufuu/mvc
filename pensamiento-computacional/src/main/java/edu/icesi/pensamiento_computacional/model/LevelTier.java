package edu.icesi.pensamiento_computacional.model;
 

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name="LEVEL_TIER")
public class LevelTier {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "code", length=20, nullable=false)
    private LevelTierCode code;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private Set<ExerciseTargetProfile> exerciseTargetProfiles = new HashSet<>(); //Se coloca el set con la clase de la relación y se llama como múltiples de esta clase :)
//falta one cuadrarle el embedded a ExerciseTargetProfile y los demás one to many, los demás embedded se deben hacer de la misma manera qeu hice el de activity_exercise
	@OneToMany(mappedBy = "levelTier", fetch = FetchType.LAZY)
    private Set<PerformanceTierHistory> performanceTierHistories = new HashSet<>();
}
