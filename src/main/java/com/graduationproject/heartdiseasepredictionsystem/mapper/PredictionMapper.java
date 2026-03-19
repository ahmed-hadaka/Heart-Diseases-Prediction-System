package com.graduationproject.heartdiseasepredictionsystem.mapper;

import com.graduationproject.heartdiseasepredictionsystem.dto.PredictionDTO;
import com.graduationproject.heartdiseasepredictionsystem.model.Prediction;
import jakarta.persistence.Column;

import java.util.List;

public class  PredictionMapper {

    public static PredictionDTO toDTO(Prediction prediction){
        PredictionDTO predictionDTO = new PredictionDTO();
        predictionDTO.setDateAndTime(prediction.getCreatedAt());
        predictionDTO.setResult(prediction.getPredictionResult());
        predictionDTO.setRiskScore(prediction.getRiskScore());
        predictionDTO.setBelongsTo(prediction.getPatient().getEmail());
        return predictionDTO;
    }
}
