package com.erp.erp.employeeLevel;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class EmployeeLevel {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Integer id;
   @Column(unique = true)
   String level;
   String levelDescription;
   @OneToMany(mappedBy = "employeeLevel")
   List<EmployeeSubLevel> subLevel;

}
