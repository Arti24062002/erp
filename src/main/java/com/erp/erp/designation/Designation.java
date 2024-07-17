package com.erp.erp.designation;



import com.erp.erp.department.Department;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
     @Column(unique = true)
    String designationName;
    @ManyToOne
    @JsonBackReference
    Department department;
    @Override
    public String toString() {
        return "Designation [id=" + id + ", designationName=" + designationName + ", designationShortName="
               ;
    }

}
