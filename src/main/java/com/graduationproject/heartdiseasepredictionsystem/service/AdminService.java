package com.graduationproject.heartdiseasepredictionsystem.service;

import com.graduationproject.heartdiseasepredictionsystem.dto.DoctorDTO;
import com.graduationproject.heartdiseasepredictionsystem.dto.PersonDTO;
import com.graduationproject.heartdiseasepredictionsystem.dto.PredictionDTO;
import com.graduationproject.heartdiseasepredictionsystem.exception.EmailAlreadyUsedException;
import com.graduationproject.heartdiseasepredictionsystem.exception.UserNotFoundException;
import com.graduationproject.heartdiseasepredictionsystem.mapper.DoctorMapper;
import com.graduationproject.heartdiseasepredictionsystem.mapper.PatientMapper;
import com.graduationproject.heartdiseasepredictionsystem.mapper.PersonMapper;
import com.graduationproject.heartdiseasepredictionsystem.mapper.PredictionMapper;
import com.graduationproject.heartdiseasepredictionsystem.model.Doctor;
import com.graduationproject.heartdiseasepredictionsystem.model.Patient;
import com.graduationproject.heartdiseasepredictionsystem.model.Person;
import com.graduationproject.heartdiseasepredictionsystem.model.Prediction;
import com.graduationproject.heartdiseasepredictionsystem.repository.DoctorRepository;
import com.graduationproject.heartdiseasepredictionsystem.repository.PersonRepository;
import com.graduationproject.heartdiseasepredictionsystem.repository.PredictionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {
    private final PersonRepository personRepository;
    private final DoctorRepository doctorRepository;
    private PredictionRepository predictionRepository;

    public AdminService(PersonRepository personRepository, PatientService patientService, DoctorService doctorService, PredictionRepository predictionRepository, DoctorRepository doctorRepository) {
        this.personRepository = personRepository;
        this.predictionRepository =predictionRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<PersonDTO> getAllUsersExceptAdmins(String email) {
        //get all users from person repository
        List<Person> users = personRepository.findAll();
        // loop and except admins
        return  users.stream().filter(user -> !user.getRole().getName().equals("ADMIN"))
                .map(PersonMapper:: toDto).toList();
    }

    public PersonDTO viewUser(Long id) {
       Optional<Person> person = personRepository.findById(id);

        if(person.isEmpty() || person.get().getRole().getName().equals("ADMIN"))
            throw new UserNotFoundException("No users with id: "+id);

         PersonDTO personDTO = PersonMapper.toDto(person.get());

            return personDTO;
    }

    public void deleteUser(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if(person.isEmpty() || person.get().getRole().getName().equals("ADMIN"))
            throw new UserNotFoundException("No users with id: "+id);

        personRepository.deleteById(id);
    }

    public List<PredictionDTO> getAllPredictions() {
        List<Prediction> predictionList =  predictionRepository.findAll();
        return predictionList.stream().map(PredictionMapper:: toDTO).toList();
    }

    public Map<String, String> registerNewDoctor(DoctorDTO doctorDTO) {
        if(doctorRepository.existsByEmail(doctorDTO.getEmail())){
            throw new EmailAlreadyUsedException("Email already in use!");
        }
        Doctor doctor = doctorRepository.save( DoctorMapper.toDoctorEntity(doctorDTO));

        Map<String, String> map = new HashMap<>();
        map.put("message","Registered Successfully");
        map.put("role",doctor.getRole().getName());
        return map;
    }
}
