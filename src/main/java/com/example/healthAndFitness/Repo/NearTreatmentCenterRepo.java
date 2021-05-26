package com.example.healthAndFitness.Repo;

import com.example.healthAndFitness.Entity.NearTreatmentCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NearTreatmentCenterRepo extends JpaRepository<NearTreatmentCenterEntity,Integer> {
    @Query(value = "select * from near_treatment_center where" +
            " concat(lower(center_location),' ',lower(center_name))" +
            " like %:s% order by center_name " +
            "limit 20",nativeQuery = true)
    List<NearTreatmentCenterEntity> getLocation(String s);

    List<NearTreatmentCenterEntity> findByCenterName(String name);
}
