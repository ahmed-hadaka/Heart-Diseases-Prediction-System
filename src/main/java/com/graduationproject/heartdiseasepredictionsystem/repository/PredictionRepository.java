package com.graduationproject.heartdiseasepredictionsystem.repository;

import com.graduationproject.heartdiseasepredictionsystem.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
