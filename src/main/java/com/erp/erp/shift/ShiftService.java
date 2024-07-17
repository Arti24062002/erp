package com.erp.erp.shift;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShiftService {
    private final ShiftRepoistery shiftRepoistery;
    private final ShiftSessionRepoistery shiftSessionRepoistery;

    public void addShift(ShiftRequest shiftRequest) {
        if (!isShiftExistByName(shiftRequest.shiftName())) {
            Shift shift = ShiftModelMapper.toShift(shiftRequest);
            shift.setShiftSession(SessionCalculator.sessionCalucaute(shiftRequest.loginTime(),
                    shiftRequest.logoutTime(), shiftRequest.noOfSessions(), shiftRequest.extraTimeToWorkInHours(),
                    shift));
            shiftRepoistery.save(shift);

        } else
            throw new ShiftException("Shift Already exist with this name");
    }

    public ShiftResponse updateShift(Integer ShiftId, ShiftRequest shiftRequest) {
        if (isShiftExistById(ShiftId)) {
            Shift exShift = getShiftExistById(ShiftId);
            if (!isShiftExistByName(shiftRequest.shiftName(),exShift)) {
                
                System.out.println("Dleetet data ");
                deleteShiftSession(exShift);
                // exShift.setShiftSession(null);
                System.out.println(exShift);
                exShift = ShiftModelMapper.fromShiftToShiftRequest(exShift, shiftRequest);

                exShift.setShiftSession(
                        SessionCalculator.sessionCalucaute(shiftRequest.loginTime(), shiftRequest.logoutTime(),
                                shiftRequest.noOfSessions(), shiftRequest.extraTimeToWorkInHours(), exShift));
                shiftRepoistery.save(exShift);
                System.out.println("Calling aggain");
                return ShiftModelMapper.toShiftResponse(exShift);

            } else
                throw new ShiftException("Cant update with this name  because shift i aleady exist");
        } else
            throw new ShiftException("Shift does not exist");
    }

    public boolean isShiftExistById(Integer id) {
        return shiftRepoistery.findById(id).isPresent();
    }

    public boolean isShiftExistByName(String name) {
        return shiftRepoistery.findAll().stream().filter(fn -> fn.getShiftName().equals(name)).findFirst().isPresent();
    }
    public boolean isShiftExistByName(String name,Shift shift) {
        return shiftRepoistery.findAll().stream().filter(fn -> fn.getShiftName().equals(name)&& fn.getId()!=shift.getId()).findFirst().isPresent();
    }

    public Shift getShiftExistById(Integer id) {
        return shiftRepoistery.findById(id).orElse(null);
    }

    public Shift getShiftExistByIdOrElseThrowException(Integer id) {
        Shift orElse = shiftRepoistery.findById(id).orElse(null);
        ;
        if (orElse != null)
            return orElse;
        else
            throw new ShiftException("Shift not found with this Id");
    }

    public ShiftResponse getShiftResponseExistById(Integer id) {
        Shift shift = shiftRepoistery.findById(id).orElse(null);
        return ShiftModelMapper.toShiftResponse(shift);
    }

    public void deleteShift(int id) {
        if (isShiftExistById(id)) {

            shiftRepoistery.deleteById(id);

        } else
            throw new ShiftException("Shift does not exist with this is");
    }

    public List<ShiftResponse> getAllShifts() {
        return shiftRepoistery.findAll().stream().map(ShiftModelMapper::toShiftResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteShiftSession(Shift exShift) {
        System.out.println(exShift.getShiftSession());
        exShift.getShiftSession().stream().forEach(fn -> {
            fn.setShift(null);
            shiftSessionRepoistery.save(fn);

            shiftSessionRepoistery.delete(fn);

        });
    }
}
