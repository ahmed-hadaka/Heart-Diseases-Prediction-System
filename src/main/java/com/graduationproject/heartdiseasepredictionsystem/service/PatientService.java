package com.graduationproject.heartdiseasepredictionsystem.service;

import com.graduationproject.heartdiseasepredictionsystem.dto.PatientDTO;
import com.graduationproject.heartdiseasepredictionsystem.exception.EmailAlreadyUsedException;
import com.graduationproject.heartdiseasepredictionsystem.exception.UserNotFoundException;
import com.graduationproject.heartdiseasepredictionsystem.mapper.PatientMapper;
import com.graduationproject.heartdiseasepredictionsystem.model.Doctor;
import com.graduationproject.heartdiseasepredictionsystem.model.Patient;
import com.graduationproject.heartdiseasepredictionsystem.repository.DoctorRepository;
import com.graduationproject.heartdiseasepredictionsystem.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    public PatientService(PatientRepository patientRepository, DoctorService docotorService,DoctorRepository doctorRepository){
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public Patient getPatientByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if(patient.isEmpty())
            throw new UserNotFoundException("User With Email = "+email+" Not Found");
        return patient.get();
    }

    public Map<String,String> registerNewPatient(@Valid  PatientDTO patientDTO){
        if(patientRepository.existsByEmail(patientDTO.getEmail())){
            throw new EmailAlreadyUsedException("Email already in use!");
        }
        Patient patient = patientRepository.save( PatientMapper.toPatientEntity(patientDTO,"SAVE",null));

        Map<String, String> map = new HashMap<>();
        map.put("message","Registered Successfully");
        map.put("role",patient.getRole().getName());
        return map;
    }

    public String updatePatient(@Valid PatientDTO patientDTO, Patient existingPatient) {
        Patient patient  = PatientMapper.toPatientEntity(patientDTO,"EDIT",existingPatient);

        Patient savedPatient = patientRepository.save(patient);
        String result = "Patient Saved successfully.";
        if(savedPatient == null)
            result = "Error:Not saved!";
        return result;
    }

    public void bookAppointment(String patientEmail, Long doctorId) {
        Optional<Patient> patient = patientRepository.findByEmail(patientEmail);
        if(patient.isEmpty()){
            throw new UserNotFoundException("No such Patient with this Email: "+patientEmail);
        }

        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if(doctor.isEmpty())
            throw new UserNotFoundException("no users with id: "+ doctorId);

        doctor.get().getPatientList().add(patient.get());
        patient.get().setDoctor(doctor.get());
        patient.get().setBookingDateAndTime(LocalDateTime.now());
        doctorRepository.save(doctor.get());
        patientRepository.save(patient.get());
    }
}
