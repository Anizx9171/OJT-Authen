package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.IncorrectAttendanceStatusException;
import com.rikkei.managementuser.exception.ModuleCourseDoesNotExistException;
import com.rikkei.managementuser.exception.ModuleIsNotPartOfTheCourseException;
import com.rikkei.managementuser.exception.NoPermissionToDelete;
import com.rikkei.managementuser.model.dto.request.AttendanceRequest;
import com.rikkei.managementuser.model.entity.Attendance;
import com.rikkei.managementuser.model.entity.AttendanceStatus;
import com.rikkei.managementuser.model.entity.Class;
import com.rikkei.managementuser.model.entity.Student;
import com.rikkei.managementuser.repository.IAttendanceRepository;
import com.rikkei.managementuser.repository.IClassRepository;
import com.rikkei.managementuser.repository.ILessonRepository;
import com.rikkei.managementuser.repository.IStudentRepository;
import com.rikkei.managementuser.service.IAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AttendanceService implements IAttendanceService {
    private final IStudentRepository studentRepository;
    private final IAttendanceRepository attendanceRepository;
    private final IClassRepository classRepository;
    private final ILessonRepository lessonRepository;


    @Override
    public void save(AttendanceRequest attendanceRequest) throws NoSuchElementException, ModuleCourseDoesNotExistException, ModuleIsNotPartOfTheCourseException {
        Class aClass = classRepository.findById(attendanceRequest.getClassId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy lớp"));
        List<Student> students = studentRepository.findAllByAClass(aClass);
        for (Student stu : students) {
            attendanceRepository.save(
                    Attendance.builder()
                            .lesson(lessonRepository.findById(attendanceRequest.getLessonId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy buổi học")))
                            .aClass(aClass)
                            .student(stu)
                            .attendanceStatus(AttendanceStatus.ABSENCE_WITHOUT_PERMISSION)
                            .build()
            );
        }
    }

    @Override
    public void changeStatus(String status, Long id) throws IncorrectAttendanceStatusException, NoSuchElementException {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm đươc điểm danh với id cung cấp"));
        switch (status) {
            case "ABSENCE_WITHOUT_PERMISSION":
                attendance.setAttendanceStatus(AttendanceStatus.ABSENCE_WITHOUT_PERMISSION);
                break;
            case "PRESENCE":
                attendance.setAttendanceStatus(AttendanceStatus.PRESENCE);
                break;
            case "ABSENCE_WITH_PERMISSION":
                attendance.setAttendanceStatus(AttendanceStatus.ABSENCE_WITH_PERMISSION);
                break;
            default:
                throw new IncorrectAttendanceStatusException("Không tồn tại trạng thái này");
        }
        attendanceRepository.save(attendance);
    }

    @Override
    public Attendance findById(Long id) throws NoSuchElementException {
        return attendanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm đươc điểm danh với id cung cấp"));
    }

    @Override
    public void delete(Long id) throws NoPermissionToDelete {
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            attendanceRepository.delete(attendanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm điểm danh học với id cung cấp")));
        } else {
            throw new NoPermissionToDelete("Bạn không có quyền xóa buổi điểm danh");
        }
    }

    @Override
    public Page<Attendance> findAll(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 1, pageable.getSort());
        Page<Attendance> attendances = attendanceRepository.findAll(pageable);

        return attendances.map(a -> Attendance.builder()
                .id(a.getId())
                .lesson(a.getLesson())
                .aClass(a.getAClass())
                .student(a.getStudent())
                .attendanceStatus(a.getAttendanceStatus())
                .build());
    }
}
