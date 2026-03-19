package com.graduationproject.heartdiseasepredictionsystem.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.ReadOnlyProperty;

@Data

public class PersonDTO {
    @Size(min = 3, message = "at least 3 characters required in the password")
    private String userName;

    //TODO: private String photo;

    private String email; // read only


    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^(?:\\+20|20|0)?1(0|1|2|5)[0-9]{8}$",
            message = "Invalid Egyptian mobile number!"
    )
    private String contactNumber;

    @Size(min = 2, message = "at least 2 characters required in the password")
    @Range(max = 150)
    private String age;

    @NotBlank(message = "Street is required")
    @Size(min = 3, max = 100)
    private String streetAddress;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50)
    private String city;

    @NotBlank(message = "State is required")
    @Size(min = 2, max = 50)
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    private String roleName; // read only

}
