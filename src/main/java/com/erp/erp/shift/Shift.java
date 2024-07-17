package com.erp.erp.shift;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String shiftName;
    String shiftCode;
    
    String loginTime;
    String logoutTime;
    String satOff;
    boolean sunOff;
    int breakInMinute ;
    int noOfSessions;
    int extraMinuteToWork;
    @OneToMany(mappedBy = "shift",cascade = CascadeType.ALL)
    @JsonManagedReference
    List<ShiftSession> ShiftSession;
    boolean status;
}