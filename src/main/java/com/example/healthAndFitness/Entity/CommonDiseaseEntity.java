package com.example.healthAndFitness.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "common_disease")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonDiseaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int diseaseId;
    String diseaseName;
    String drugName;
}
