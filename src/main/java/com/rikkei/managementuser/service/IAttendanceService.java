package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.entity.Attendance;
import com.rikkei.managementuser.model.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;

public interface IAttendanceService {

    void save(AttendanceRequest attendanceRequest) throws NoSuchElementException, ModuleCourseDoesNotExistException, ModuleIsNotPartOfTheCourseException;

    void changeStatus(String status, Long id) throws IncorrectAttendanceStatusException;

    Attendance findById(Long id);

    void delete(Long id) throws NoPermissionToDelete;

    Page<Attendance> findAll(Pageable pageable);
}
