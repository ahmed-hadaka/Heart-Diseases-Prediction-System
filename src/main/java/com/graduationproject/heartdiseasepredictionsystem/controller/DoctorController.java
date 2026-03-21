package com.graduationproject.heartdiseasepredictionsystem.controller;

import com.graduationproject.heartdiseasepredictionsystem.dto.PatientsDTOList;
import com.graduationproject.heartdiseasepredictionsystem.dto.PredictionDTO;
import com.graduationproject.heartdiseasepredictionsystem.model.Prescription;
import com.graduationproject.heartdiseasepredictionsystem.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<PredictionDTO>> viewPatientPredictionsList(@PathVariable(name = "id") Long patientId, Authentication authentication) throws Exception {
        // get person from persistence layer
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String doctorEmail = userDetails.getUsername();

        List<PredictionDTO> predictionsDTOs = doctorService.viewPatientPredictionsList(patientId,doctorEmail);
        return ResponseEntity.ok(predictionsDTOs);
    }

    @GetMapping("write-prescription/{patientId}")
    public ResponseEntity<Prescription> writePrescription(@PathVariable Long patientId, Authentication authentication) throws Exception {
        // get person from persistence layer
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String doctorEmail = userDetails.getUsername();

        Prescription prescription = doctorService.writePrescription(patientId,doctorEmail);

        return ResponseEntity.ok(prescription);
    }

    @PostMapping("save-prescription")
    public ResponseEntity<String> savePrescription(@RequestBody Prescription prescription, Authentication authentication) throws Exception {

        doctorService.savePrescription(prescription);

        return ResponseEntity.ok("prescription saved successfully!");
    }

}
