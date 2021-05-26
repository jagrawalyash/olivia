package com.example.healthAndFitness.Repo;

import com.example.healthAndFitness.Entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DoctorRepo extends JpaRepository<DoctorEntity,Integer> {
    @Query(value = "select * from doctor where " +
            "concat(doctor_name,' ' , doctor_experience,' ',doctor_location) like %:s% limit 20",nativeQuery = true)
    List<DoctorEntity> getNames(String s);

    List<DoctorEntity> findAllByDoctorName(String name);
}
