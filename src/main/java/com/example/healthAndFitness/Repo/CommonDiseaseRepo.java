package com.example.healthAndFitness.Repo;

import com.example.healthAndFitness.Entity.CommonDiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommonDiseaseRepo extends JpaRepository<CommonDiseaseEntity,Integer> {
    @Query(value = "SELECT * FROM common_disease WHERE lower(disease_name) LIKE %:s% LIMIT 10",
            nativeQuery = true)
    List<CommonDiseaseEntity> searchForGeneral(String s);
}
