package com.example.healthAndFitness.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int doctorId;
    String doctorName;
    @Column(columnDefinition = "text")
    String doctorExperience;
    String doctorLocation;
    @Column(columnDefinition = "text")
    String doctorDescription;
}
