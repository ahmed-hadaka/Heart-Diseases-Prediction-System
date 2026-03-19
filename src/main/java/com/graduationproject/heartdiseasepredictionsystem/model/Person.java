package com.graduationproject.heartdiseasepredictionsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, message = "at least 3 characters required in the name")
    private String name;

    @Column(nullable = false)
    @Size(min = 3, message = "at least 3 characters required in the password")
    private String userName;

    @Column(nullable = false,unique = true)
    @Email(message = "Email is Invalid")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must contain a valid domain like .com or .net"
    )
    private String email;

    @Column(nullable = false)
    @Size(min = 5, message = "at least 5 characters required in the password")
    private String password;

    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$",message = "Invalid Contact Number!")
    private String contactNumber;

    @Size(min = 2, message = "at least 2 characters required in the password")
    @Range(max = 150)
    private String age;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn // default: nullable = true
    private Address address;//one to one- uni

    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;//Many to one - uni
}
