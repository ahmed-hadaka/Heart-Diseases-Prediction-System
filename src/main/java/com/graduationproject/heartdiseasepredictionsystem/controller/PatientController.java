package com.graduationproject.heartdiseasepredictionsystem.controller;

import com.graduationproject.heartdiseasepredictionsystem.dto.DoctorDTOList;
import com.graduationproject.heartdiseasepredictionsystem.dto.PatientDTO;
import com.graduationproject.heartdiseasepredictionsystem.mapper.PatientMapper;
import com.graduationproject.heartdiseasepredictionsystem.model.Patient;
import com.graduationproject.heartdiseasepredictionsystem.service.DoctorService;
import com.graduationproject.heartdiseasepredictionsystem.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

   private PatientService patientService;
   private DoctorService doctorService;

    public PatientController(PatientService patientService,DoctorService doctorService){
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @PostMapping("/register-new-patient")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult){
        //handle validation errors
        Map<String,String >  errors = handleValidationErrors(bindingResult);
        if(!errors.isEmpty())
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

        Map<String ,String> result =  patientService.registerNewPatient(patientDTO);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/view-personal-details")
    public ResponseEntity<PatientDTO> viewPersonalDetails(Authentication authentication){

        // get person from persistence layer
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Patient patient = patientService.getPatientByEmail(email);

        // map it to person DTO
        PatientDTO personDTO = PatientMapper.toPatientDTO(patient);

        // forward it
        return ResponseEntity.ok(personDTO);
    }

    @PostMapping("/edit-patient")
    public ResponseEntity<Map<String, String>> savePatient(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult,Authentication authentication){
        //handle validation errors
      Map<String,String >  errors = handleValidationErrors(bindingResult);
      if(!errors.isEmpty())
          return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

    // find tha authenticated user
        // get person from persistence layer
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Patient patient = patientService.getPatientByEmail(email);

        //call service.save()
        String result = patientService.editPatient(patientDTO,patient);

        return ResponseEntity.ok(Map.of("Message",result));
    }

    @GetMapping("/view-all-doctors")
    public ResponseEntity<List<DoctorDTOList>>viewAllDoctors(){
        List<DoctorDTOList> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }


    @GetMapping("/book-appointment/{doctorId}")
    public ResponseEntity<String> bookAppointment(Authentication authentication, @PathVariable Long doctorId){
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String patientEmail = userDetails.getUsername();

        patientService.bookAppointment(patientEmail, doctorId);
        return ResponseEntity.ok("Booked successfully");//TODO: specify the time of visit(appointment time)
    }

    private Map<String ,String> handleValidationErrors(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();

        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(error ->{
                map.put(error.getField(),error.getDefaultMessage());
            });
        }
        return map;
    }
}
