package com.example.healthAndFitness.Repo;

import com.example.healthAndFitness.Entity.BloodReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BloodReportRepo extends JpaRepository<BloodReportEntity,Integer> {
}
