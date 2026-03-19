package com.graduationproject.heartdiseasepredictionsystem.service;

import com.graduationproject.heartdiseasepredictionsystem.dto.DoctorDTOList;
import com.graduationproject.heartdiseasepredictionsystem.dto.PatientsDTOList;
import com.graduationproject.heartdiseasepredictionsystem.dto.PredictionDTO;
import com.graduationproject.heartdiseasepredictionsystem.exception.UserNotFoundException;
import com.graduationproject.heartdiseasepredictionsystem.mapper.DoctorMapper;
import com.graduationproject.heartdiseasepredictionsystem.mapper.PatientMapper;
import com.graduationproject.heartdiseasepredictionsystem.mapper.PredictionMapper;
import com.graduationproject.heartdiseasepredictionsystem.model.Doctor;
import com.graduationproject.heartdiseasepredictionsystem.model.Patient;
import com.graduationproject.heartdiseasepredictionsystem.model.Prediction;
import com.graduationproject.heartdiseasepredictionsystem.repository.DoctorRepository;
import com.graduationproject.heartdiseasepredictionsystem.repository.PatientRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    public DoctorService(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public List<DoctorDTOList> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorDTOList> doctorDTOsList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            doctorDTOsList.add(DoctorMapper.toDoctorDTO(doctor));
        }
        return doctorDTOsList;
    }

    public List<PatientsDTOList> getPatientList(String email) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        if (doctor.isEmpty()) {
            throw new UserNotFoundException("User With Email = " + email + " Not Found");
        }
        List<Patient> patientList = doctor.get().getPatientList();
        return patientList.stream().map(PatientMapper::toPatientDTOList).toList();
    }

    public List<PredictionDTO> viewPatientPredictionsList(Long id, String doctorEmail) throws Exception {

        Doctor doctor = doctorRepository.findByEmail(doctorEmail)
                .orElseThrow(() ->
                        new UserNotFoundException("Doctor not found"));
//        Long id = Long.valueOf(patientId);
//        Check ownership (security check)
        boolean isPatientBelongToDoctor = isPatientBelongToDoctor(id, doctor);
        if (!isPatientBelongToDoctor)
            throw new Exception("Doctor with email " + doctorEmail + " Does not have this patient in his list");

        // Get patient predictions
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isEmpty()){
            throw new UserNotFoundException("No such Patient with this id: "+id);
        }
       List<Prediction> predictions = patient.get().getPredictionList();

        //  Map to DTO list
        return predictions.stream()
                .map(PredictionMapper::toDTO)
                .toList();
    }

    private Boolean isPatientBelongToDoctor(Long id, Doctor doctor) {

        for (Patient patient : doctor.getPatientList()) {
            if (patient.getId() == id)
                return true;
        }
        return false;
    }

}
