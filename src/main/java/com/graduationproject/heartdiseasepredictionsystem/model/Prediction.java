package com.graduationproject.heartdiseasepredictionsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String predictionResult;

    private String riskScore;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient;
}
