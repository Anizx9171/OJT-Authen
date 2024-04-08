package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.AttendanceEditRequest;
import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.service.IAttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user-management/api/attendance")
@RestController
@RequiredArgsConstructor
public class AttendanceController {
    private final IAttendanceService attendanceService;
    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody AttendanceRequest attendanceRequest) throws ModuleIsNotPartOfTheCourseException, ModuleCourseDoesNotExistException, ClassNotFoundException {
        attendanceService.save(attendanceRequest);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Thêm mới thành công"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editClass(@Valid @RequestBody AttendanceEditRequest attendanceEditRequest, @PathVariable Long id) throws IncorrectAttendanceStatusException {
        attendanceService.changeStatus(attendanceEditRequest.getStatus(), id);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Đă thay đổi trạng thái thàng công"));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) throws NoPermissionToDelete {
        attendanceService.delete(id);
        return ResponseEntity.status(204).body(null);
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable){
        return ResponseEntity.status(200).body(attendanceService.findAll(pageable));
    }
}