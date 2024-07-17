package com.erp.erp.role;

import java.util.List;


import com.erp.erp.permission.Permission;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) 
   Integer id;
   String role;
   
   @ManyToMany()
   List<Permission> permission;
}
