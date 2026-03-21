package com.graduationproject.heartdiseasepredictionsystem.repository;

import com.graduationproject.heartdiseasepredictionsystem.dto.PatientsDTOList;
import com.graduationproject.heartdiseasepredictionsystem.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String  email);
    boolean existsByEmail(String email);

    Optional<Doctor>  findByName(String doctorName);
}
