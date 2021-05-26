package com.example.healthAndFitness.Repo;

import com.example.healthAndFitness.Entity.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DiseaseRepo extends JpaRepository<DiseaseEntity,Integer> {
    List<DiseaseEntity> findByDiseaseName(String name);

    @Query(value = "SELECT * FROM new_disease WHERE lower(description) LIKE %:s% LIMIT 10",
            nativeQuery = true)
    List<DiseaseEntity> searchDisease(String s);

    @Query(value = "SELECT * FROM new_disease WHERE concat(lower(disease_name),' ',lower(symptoms)) LIKE %:s% " +
            "LIMIT 30", nativeQuery =
            true)
    List<DiseaseEntity> searchForSuggestions(String s);
}
