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
import com.graduationproject.heartdiseasepredictionsystem.model.Prescription;
import com.graduationproject.heartdiseasepredictionsystem.repository.DoctorRepository;
import com.graduationproject.heartdiseasepredictionsystem.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<PredictionDTO> viewPatientPredictionsList(Long patientId, String doctorEmail) throws Exception {

        Patient patient = isPatientBelongToDoctor(patientId, doctorEmail);
        if (patient == null)
            throw new Exception("Doctor with email " + doctorEmail + " Does not have this patient in his list");

       List<Prediction> predictions = patient.getPredictionList();

        //  Map to DTO list
        return predictions.stream()
                .map(PredictionMapper::toDTO)
                .toList();
    }

    private Patient isPatientBelongToDoctor(Long id, String doctorEmail) {
        Doctor doctor = doctorRepository.findByEmail(doctorEmail)
                .orElseThrow(() ->
                        new UserNotFoundException("Doctor not found"));
        for (Patient patient : doctor.getPatientList()) {
            if (patient.getId() == id)
                return patient;
        }
        return null;
    }

    public void savePrescription(Prescription prescription) throws Exception {
       Doctor doctor = doctorRepository.findByName(prescription.getDoctorName()).orElseThrow(() ->
               new UserNotFoundException("Doctor not found"));
       Patient patient = patientRepository.findByName(prescription.getPatientName()).orElseThrow(()->
               new UserNotFoundException(("Doctor " + prescription.getDoctorName() + " Does not have this patient in his list")));

        patient.getPrescriptions().add(prescription);
        patientRepository.save(patient);
    }

    public Prescription writePrescription(Long patientId, String doctorEmail) throws Exception {
        Patient patient = isPatientBelongToDoctor(patientId, doctorEmail);
        if (patient == null) {
            throw new Exception("Doctor with email " + doctorEmail + " Does not have this patient in his list");
        }

        Doctor doctor = doctorRepository.findByEmail(doctorEmail)
                .orElseThrow(() ->
                new UserNotFoundException("Doctor not found"));

        Prescription prescription = new Prescription();
        prescription.setPatientName(patient.getName());
        prescription.setPatientAddress(patient.getAddress().getCity().concat(patient.getAddress().getCountry()));
        prescription.setDoctorName(doctor.getName());
        prescription.setDoctorSpecialization(doctor.getSpecialization());
        prescription.setPrescriptionDate(LocalDateTime.now());

        return prescription;
    }
}
