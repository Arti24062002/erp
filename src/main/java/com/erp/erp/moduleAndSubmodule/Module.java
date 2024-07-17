package com.erp.erp.moduleAndSubmodule;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data

public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String moduleName;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "module")
    @JsonIgnore
    
    List<SubModule> subModule;
    @Override
    public String toString() {
        return "Module [id=" + id + ", moduleName=" + moduleName + "]";
    }


}
