package com.erp.erp.shift;

public class ShiftModelMapper {

    public static Shift toShift(ShiftRequest shiftRequest) {
        Shift shift = new Shift();
        shift.setShiftName(shiftRequest.shiftName());
        shift.setShiftCode(shiftRequest.shiftCode());
        shift.setLoginTime(shiftRequest.loginTime());
        shift.setLogoutTime(shiftRequest.logoutTime());
        shift.setSatOff(shiftRequest.satOff());
        shift.setBreakInMinute(shiftRequest.breakInMinute());
        shift.setSunOff(shiftRequest.sunOff());
        shift.setNoOfSessions(shiftRequest.noOfSessions());
        shift.setExtraMinuteToWork(shiftRequest.extraTimeToWorkInHours());
      
        shift.setStatus(shiftRequest.status());
        return shift;
    }

    public static ShiftResponse toShiftResponse(Shift shift) {
        return new ShiftResponse(
            shift.getId(),
            shift.getShiftName(),
            shift.getShiftCode(),
            shift.getLoginTime(),
            shift.getLogoutTime(),
            shift.getBreakInMinute(),
            shift.getSatOff(),
            shift.isSunOff(),
            shift.getNoOfSessions(),
            shift.getExtraMinuteToWork(),
            shift.getShiftSession(),
            shift.isStatus()
        );
    }
    public static Shift fromShiftToShiftRequest(Shift shift,ShiftRequest shiftRequest) {
       
        shift.setShiftName(shiftRequest.shiftName());
        shift.setShiftCode(shiftRequest.shiftCode());
        shift.setLoginTime(shiftRequest.loginTime());
        shift.setLogoutTime(shiftRequest.logoutTime());
        shift.setSatOff(shiftRequest.satOff());
        shift.setBreakInMinute(shiftRequest.breakInMinute());
        shift.setSunOff(shiftRequest.sunOff());
        shift.setNoOfSessions(shiftRequest.noOfSessions());
        shift.setExtraMinuteToWork(shiftRequest.extraTimeToWorkInHours());
      
        shift.setStatus(shiftRequest.status());
        return shift;
    }
}
