package com.erp.erp.moduleAndSubmodule;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.erp.erp.permission.Permission;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class SubModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    @ManyToOne
    @JsonBackReference
    Module module;
    @OneToOne(cascade = CascadeType.ALL)
    Permission permission;
    @Override
    public String toString() {
        return "SubModule [id=" + id + ", name=" + name + ", permission=" + permission + "]";
    }
    
}
