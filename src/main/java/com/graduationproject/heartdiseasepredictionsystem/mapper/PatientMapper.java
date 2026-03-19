package com.graduationproject.heartdiseasepredictionsystem.mapper;

import com.graduationproject.heartdiseasepredictionsystem.dto.PatientDTO;
import com.graduationproject.heartdiseasepredictionsystem.dto.PatientsDTOList;
import com.graduationproject.heartdiseasepredictionsystem.dto.PredictionDTO;
import com.graduationproject.heartdiseasepredictionsystem.model.*;
import com.graduationproject.heartdiseasepredictionsystem.repository.DoctorRepository;
import com.graduationproject.heartdiseasepredictionsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PatientMapper {

   static RoleRepository roleRepository;
   static PasswordEncoder passwordEncoder;
   static DoctorRepository doctorRepository;
    public  PatientMapper(RoleRepository roleRepository, PasswordEncoder passwordEncoder, DoctorRepository doctorRepository){
        this.roleRepository= roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordEncoder = passwordEncoder;
        this. doctorRepository =doctorRepository;
    }

    public static Patient toPatientEntity(PatientDTO patientDTO,String RequestType,Patient existingPatient) {
        if(patientDTO == null)
            return null;

        Patient patient = existingPatient;
        Address address;

        if(RequestType.equals("SAVE")){
            patient = new Patient();
            patient.setEmail(patientDTO.getEmail());
            patient.setRole(roleRepository.findById(2L).get());
            Optional<Doctor> doctor = doctorRepository.findByEmail(patientDTO.getDoctorEmail());
            if(!doctor.isEmpty())
                patient.setDoctor(doctor.get());
            address = new Address();
        }else{
            address = existingPatient.getAddress();
        }

        patient.setName(patientDTO.getName());
        patient.setUserName(patientDTO.getUserName());
        patient.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
        patient.setContactNumber(patientDTO.getContactNumber());


        address.setStreetAddress(patientDTO.getStreetAddress());
        address.setCity(patientDTO.getCity());
        address.setState(patientDTO.getState());
        address.setCountry(patientDTO.getCountry());
        patient.setAddress(address);
// prediction add.


        return patient;
    }


    public static PatientDTO toPatientDTO(Patient patient) {
        if (patient == null) {
            return null;
        }

        PatientDTO dto = new PatientDTO();

        dto.setName(patient.getName());
        dto.setUserName(patient.getUserName());
        dto.setEmail(patient.getEmail());
        dto.setPassword(patient.getPassword());
        dto.setContactNumber(patient.getContactNumber());

        if (patient.getAddress() != null) {
            dto.setStreetAddress(patient.getAddress().getStreetAddress());
            dto.setCity(patient.getAddress().getCity());
            dto.setState(patient.getAddress().getState());
            dto.setCountry(patient.getAddress().getCountry());
        }

        List<PredictionDTO> predictionDTOList = new ArrayList<>();
        for(Prediction prediction : patient.getPredictionList()){
            PredictionDTO predictionDTO = new PredictionDTO();
            predictionDTO.setDateAndTime(prediction.getCreatedAt());
            predictionDTO.setRiskScore(prediction.getRiskScore());
            predictionDTO.setResult(prediction.getPredictionResult());
            predictionDTOList.add(predictionDTO);
        }
        dto.setPredictionDTOS(predictionDTOList);

        if(patient.getDoctor() != null)
            dto.setDoctorEmail(patient.getDoctor().getEmail());
        return dto;
    }

    public static PatientsDTOList toPatientDTOList(Patient patient) {
        PatientsDTOList patientsDTOList = new PatientsDTOList();

        patientsDTOList.setPatientName(patient.getName());
        patientsDTOList.setBookingDateAndTime(patient.getBookingDateAndTime());

        return patientsDTOList;
    }
}
