package com.example.healthAndFitness.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "blood_report_component")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int componentId;
    String componentName;
    String componentRange;

}
