package com.graduationproject.heartdiseasepredictionsystem.service;

import com.graduationproject.heartdiseasepredictionsystem.exception.UserNotFoundException;
import com.graduationproject.heartdiseasepredictionsystem.model.Patient;
import com.graduationproject.heartdiseasepredictionsystem.repository.PredictionRepository;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    private PredictionRepository predictionRepository;

    public PredictionService(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    public Patient getPatientOfPrediction(Long id) {
        Patient patient = null;
        if (isExist(id)) {
            patient = predictionRepository.findById(id).get().getPatient();
            if(patient == null)
                throw new UserNotFoundException("There is no user for prediction id: " + id);
        }
        return patient;
    }

    private boolean isExist(Long id) {
        if (!predictionRepository.existsById(id)) {
            throw new UserNotFoundException("There is no prediction with id: " + id);
        }
        return true;
    }

    public void deleteById(Long id) {
        predictionRepository.deleteById(id);
    }
}
