package com.example.healthAndFitness.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "new_drug")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrugEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int drugId;
    @Column(columnDefinition = "text")
    String useOfDrug;
    @Column(columnDefinition = "text")
    String drugName;
}
