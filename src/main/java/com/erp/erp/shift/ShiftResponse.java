package com.erp.erp.shift;

import java.util.List;

public record ShiftResponse(
    int id,
    String shiftName,
    String shiftCode,
    String loginTime,
    String logoutTime,
    int breakInMinute,
    String satOff,
    boolean sunOff,
    int noOfSessions,
    int extraHoursToWork,
    List<ShiftSession> shiftSession,
    boolean status
) {
    
}
