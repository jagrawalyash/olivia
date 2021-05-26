package com.example.healthAndFitness.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "near_treatment_center")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NearTreatmentCenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int centerId;
    @Column(columnDefinition = "text")
    String centerName;
    String contactNumber;
    @Column(columnDefinition = "text")
    String centerLocation;
    String city;
}
