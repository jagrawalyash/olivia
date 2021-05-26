package com.example.healthAndFitness.Entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "new_disease")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiseaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String diseaseName;
    @Column(columnDefinition = "text")
    String description,symptoms;
    String diseaseType;

}
