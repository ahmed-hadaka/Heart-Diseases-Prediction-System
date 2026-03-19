package com.graduationproject.heartdiseasepredictionsystem.repository;

import com.graduationproject.heartdiseasepredictionsystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Optional<Person> findByEmail(String username);
}
