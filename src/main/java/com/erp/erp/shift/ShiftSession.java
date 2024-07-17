package com.erp.erp.shift;


import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class ShiftSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String inTime;
    String outTime;
    @ManyToOne
    @JsonBackReference
    Shift shift;
    @Override
    public String toString() {
        return "ShiftSession [id=" + id + ", name=" + name + ", inTime=" + inTime + ", outTime=" + outTime + "]";
    }

}
