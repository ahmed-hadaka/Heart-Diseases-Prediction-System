package com.graduationproject.heartdiseasepredictionsystem.controller;

import com.graduationproject.heartdiseasepredictionsystem.dto.PatientsDTOList;
import com.graduationproject.heartdiseasepredictionsystem.dto.PredictionDTO;
import com.graduationproject.heartdiseasepredictionsystem.model.Doctor;
import com.graduationproject.heartdiseasepredictionsystem.model.Patient;
import com.graduationproject.heartdiseasepredictionsystem.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/doctor/")
public class DoctorController {

    DoctorService doctorService;

    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @GetMapping("view-patients")
    public ResponseEntity<List<PatientsDTOList>> viewPatients(Authentication authentication){
        // get person from persistence layer
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        List<PatientsDTOList> patientsDTOList = doctorService.getPatientList(email);
        return ResponseEntity.ok(patientsDTOList);
    }

    @GetMapping("view-patient-predictionsList/{id}")
    public ResponseEntity<List<PredictionDTO>> viewPatientPredictionsList(@PathVariable Long id, Authentication authentication) throws Exception {
        // get person from persistence layer
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        List<PredictionDTO> predictionsDTOs = doctorService.viewPatientPredictionsList(id,email);
        return ResponseEntity.ok(predictionsDTOs);
    }
}
