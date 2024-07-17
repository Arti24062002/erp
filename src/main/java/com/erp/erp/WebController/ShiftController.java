package com.erp.erp.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.erp.shift.ShiftService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShiftController {
    private final ShiftService shiftService;

    @GetMapping("/shifts")
    public String getEmployeeLevel(Model m) {
        m.addAttribute("allShift", shiftService.getAllShifts());
        return "shift-list";
    }
}
