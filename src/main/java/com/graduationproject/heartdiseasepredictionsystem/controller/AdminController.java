package com.graduationproject.heartdiseasepredictionsystem.controller;

import com.graduationproject.heartdiseasepredictionsystem.dto.DoctorDTO;
import com.graduationproject.heartdiseasepredictionsystem.dto.PersonDTO;
import com.graduationproject.heartdiseasepredictionsystem.dto.PredictionDTO;
import com.graduationproject.heartdiseasepredictionsystem.model.Patient;
import com.graduationproject.heartdiseasepredictionsystem.repository.PredictionRepository;
import com.graduationproject.heartdiseasepredictionsystem.service.AdminService;
import com.graduationproject.heartdiseasepredictionsystem.service.PredictionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.graduationproject.heartdiseasepredictionsystem.controller.PatientController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/")
public class AdminController {

    private final PredictionService predictionService;
    private AdminService adminService;

    public AdminController(AdminService adminService, PredictionRepository predictionRepository, PredictionService predictionService){
        this.adminService = adminService;
        this.predictionService = predictionService;
    }

    @GetMapping("view-all-users")
    public ResponseEntity<List<PersonDTO>>viewAllUsers(Authentication authentication){
        // get the authenticated person from persistence layer
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        
        List<PersonDTO> users =  adminService.getAllUsersExceptAdmins(email);
        
        return  ResponseEntity.ok(users);
    }

    @GetMapping("view-user/{id}")
    public ResponseEntity<PersonDTO> viewUser(@PathVariable Long id){
        PersonDTO personDTO = adminService.viewUser(id);
        return ResponseEntity.ok(personDTO);
    }

    @PostMapping("delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        adminService.deleteUser(id);
        return ResponseEntity.ok("User Id: "+id+" Deleted successfully");
    }

    @GetMapping("view-all-predictions")
    public  ResponseEntity<List<PredictionDTO>> viewAllPredictions(){
       List<PredictionDTO> predictionList =  adminService.getAllPredictions();

       return ResponseEntity.ok(predictionList);
    }

    @GetMapping("get-patient-of-prediction/{id}")
    public ResponseEntity<PersonDTO> getPatientOfPrediction(@PathVariable Long id){
        Patient patient = predictionService.getPatientOfPrediction(id);
        return viewUser(patient.getId());
    }

    @PostMapping("delete-prediction/{id}")
    public ResponseEntity<String> deletePrediction(@PathVariable Long id){
        predictionService.deleteById(id);
        return ResponseEntity.ok("Prediction with id: "+id+" has been deleted.");
    }

    @PostMapping("add-doctor")
    public ResponseEntity<Map<String ,String >> addDoctor(@RequestBody @Valid DoctorDTO doctorDTO, BindingResult bindingResult){
        //handle validation errors
        Map<String,String > errors = handleValidationErrors(bindingResult);
        if(!errors.isEmpty())
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        Map<String ,String> result =  adminService.registerNewDoctor(doctorDTO);
        return ResponseEntity.ok(result);

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
