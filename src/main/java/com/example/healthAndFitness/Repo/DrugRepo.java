package com.example.healthAndFitness.Repo;

import com.example.healthAndFitness.Entity.DrugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DrugRepo extends JpaRepository<DrugEntity,Integer> {
    @Query(value = "SELECT * FROM new_drug WHERE lower(use_of_drug) LIKE %:s% LIMIT 10",
            nativeQuery = true)
    List<DrugEntity> searchDrug(String s);
    @Query(value = "SELECT * FROM new_drug WHERE concat(lower(drug_name)) LIKE %:s% LIMIT 30 ",
            nativeQuery =
            true)
    List<DrugEntity> searchForSuggestions(String s);

    List<DrugEntity> findByDrugName(String s);

    int deleteByDrugName(String name);
}
