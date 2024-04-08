package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Attendance;
import com.rikkei.managementuser.model.entity.Lesson;
import com.rikkei.managementuser.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> findAllByLesson(Lesson lesson);
}
