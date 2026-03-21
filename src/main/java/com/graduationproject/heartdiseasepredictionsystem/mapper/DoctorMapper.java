package com.graduationproject.heartdiseasepredictionsystem.mapper;

import com.graduationproject.heartdiseasepredictionsystem.dto.DoctorDTO;
import com.graduationproject.heartdiseasepredictionsystem.dto.DoctorDTOList;
import com.graduationproject.heartdiseasepredictionsystem.model.Address;
import com.graduationproject.heartdiseasepredictionsystem.model.Doctor;
import com.graduationproject.heartdiseasepredictionsystem.model.Role;
import com.graduationproject.heartdiseasepredictionsystem.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public static RoleRepository roleRepository;
    public static PasswordEncoder passwordEncoder;
    public DoctorMapper(RoleRepository roleRepository,PasswordEncoder passwordEncoder){
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static DoctorDTOList toDoctorDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDTOList dto = new DoctorDTOList();

        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setContactNumber(doctor.getContactNumber());

        Address address = doctor.getAddress();

        dto.setStreetAddress(address.getStreetAddress());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());

        dto.setSpecialization(doctor.getSpecialization());
        dto.setWorkTime(doctor.getWorkTime());

        return dto;
    }

    public static Doctor toDoctorEntity(DoctorDTO dto) {
        if (dto == null) {
            return null;
        }

        Doctor doctor = new Doctor();

        // Mapping Fields from Person (inherited)
        doctor.setName(dto.getName());
        doctor.setUserName(dto.getUserName());
        doctor.setEmail(dto.getEmail());
        doctor.setPassword(passwordEncoder.encode(dto.getPassword()));
        doctor.setContactNumber(dto.getContactNumber());
        doctor.setAge(dto.getAge());

        // Mapping Address (Assuming you have an Address object in Doctor/Person)
        Address address = new Address();
        address.setStreetAddress(dto.getStreetAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        doctor.setAddress(address);

        // Mapping Doctor-specific fields
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setWorkTime(dto.getWorkTime());
        doctor.setRole(roleRepository.findById(3L).get());

        return doctor;
    }
}
