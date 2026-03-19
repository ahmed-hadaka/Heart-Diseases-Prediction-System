package com.graduationproject.heartdiseasepredictionsystem.repository;

import com.graduationproject.heartdiseasepredictionsystem.model.Patient;
import com.graduationproject.heartdiseasepredictionsystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    boolean existsByEmail(String email);
}
