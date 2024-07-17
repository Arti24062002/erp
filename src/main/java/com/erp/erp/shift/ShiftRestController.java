package com.erp.erp.shift;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class ShiftRestController {
    private final ShiftService shiftService;

    @GetMapping("/shifts")
    public ResponseEntity<List<ShiftResponse>> getAllShifts() {
        return ResponseEntity.status(HttpStatus.OK).body(shiftService.getAllShifts());
    }

    @GetMapping("/shifts/{id}")
    public ResponseEntity<ShiftResponse> getShiftById(@PathVariable Integer id) {
        ShiftResponse shiftResponse = shiftService.getShiftResponseExistById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(shiftResponse);
       
    }

    @PostMapping("/add-shift")
    public ResponseEntity<Void> addShift(@Valid @RequestBody ShiftRequest shiftRequest) {
        shiftService.addShift(shiftRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-shift/{id}")
    public ResponseEntity<ShiftResponse> updateShift(@PathVariable Integer id, @Valid @RequestBody ShiftRequest shiftRequest) {
       return ResponseEntity.status(HttpStatus.OK).body(shiftService.updateShift(id, shiftRequest));
    }

    @DeleteMapping("/delete-shift/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Integer id) {
        shiftService.deleteShift(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
