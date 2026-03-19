package com.graduationproject.heartdiseasepredictionsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Patient extends Person{

    @ManyToOne
    private Doctor doctor;

    private String bookingDateAndTime;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Prediction> predictionList = new ArrayList<>();// one to many - uni

}
