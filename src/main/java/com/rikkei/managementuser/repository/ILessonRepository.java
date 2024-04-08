package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Courses;
import com.rikkei.managementuser.model.entity.Lesson;
import com.rikkei.managementuser.model.entity.ModuleCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ILessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByModuleCourseAndCourses(ModuleCourse moduleCourse, Courses courses);
    Optional<Lesson> findBySessionNameIgnoreCase(String title);
}
