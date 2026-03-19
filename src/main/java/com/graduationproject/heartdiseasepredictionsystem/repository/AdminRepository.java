package com.graduationproject.heartdiseasepredictionsystem.repository;

import com.graduationproject.heartdiseasepredictionsystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Person, Long> {
}
