package com.erp.erp.employeeLevel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmployeeSubLevel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer id;
    @Column(unique=true)
    String subLevel;
    
    String subLevelTitle;
    String subLevelDiscription;
    String subLevelQualification;
    @JsonIgnore
    @ManyToOne
    EmployeeLevel employeeLevel;

}
