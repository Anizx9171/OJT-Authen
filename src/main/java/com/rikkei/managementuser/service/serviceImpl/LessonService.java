package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.LessonRequest;
import com.rikkei.managementuser.model.dto.response.TeacherResponse;
import com.rikkei.managementuser.model.entity.Courses;
import com.rikkei.managementuser.model.entity.Lesson;
import com.rikkei.managementuser.model.entity.ModuleCourse;
import com.rikkei.managementuser.model.entity.Teacher;
import com.rikkei.managementuser.repository.ICourseRepository;
import com.rikkei.managementuser.repository.ILessonRepository;
import com.rikkei.managementuser.repository.IModuleCourseRepository;
import com.rikkei.managementuser.service.ILessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LessonService implements ILessonService {
    private final ILessonRepository lessonRepository;
    private final ICourseRepository courseRepository;
    private final IModuleCourseRepository moduleCourseRepository;

    @Override
    public void save(LessonRequest lessonRequest) throws ModuleCourseDoesNotExistException, ModuleIsNotPartOfTheCourseException, ClassNotFoundException {
        Lesson newLesson = Lesson.builder()
                .courses(courseRepository.findById(lessonRequest.getCoursesId()).orElseThrow(() -> new ClassNotFoundException("Khóa học không tồn tại")))
                .time(lessonRequest.getTime())
                .moduleCourse(moduleCourseRepository.findById(lessonRequest.getModuleCourseId()).orElseThrow(() -> new ModuleCourseDoesNotExistException("Module học không tồn tại")))
                .sessionName(lessonRequest.getSessionName())
                .build();
        if (!newLesson.getCourses().getCourseId().equals(newLesson.getModuleCourse().getCourse().getCourseId())) {
            throw new ModuleIsNotPartOfTheCourseException("Module không thuộc khóa học này");
        }
        lessonRepository.save(newLesson);
    }

    @Override
    public void edit(LessonRequest lessonRequest, Long id) throws LessonExistException, ModuleCourseDoesNotExistException, ModuleIsNotPartOfTheCourseException, ClassNotFoundException {
        lessonRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Không tìm đươc khóa học với id cung cấp"));
        if (lessonRepository.findBySessionNameIgnoreCase(lessonRequest.getSessionName()).orElse(null) != null){
            throw new LessonExistException("Tên session đã tồn tại");
        }
        Lesson newLesson = Lesson.builder()
                .id(id)
                .courses(courseRepository.findById(lessonRequest.getCoursesId()).orElseThrow(() -> new ClassNotFoundException("Khóa học không tồn tại")))
                .time(lessonRequest.getTime())
                .moduleCourse(moduleCourseRepository.findById(lessonRequest.getModuleCourseId()).orElseThrow(() -> new ModuleCourseDoesNotExistException("Module học không tồn tại")))
                .sessionName(lessonRequest.getSessionName())
                .build();
        if (!newLesson.getCourses().getCourseId().equals(newLesson.getModuleCourse().getCourse().getCourseId())) {
            throw new ModuleIsNotPartOfTheCourseException("Module không thuộc khóa học này");
        }
        lessonRepository.save(newLesson);
    }

    @Override
    public Lesson findById(Long id) {
        return  lessonRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Không tìm đươc khóa học với id cung cấp"));
    }

    @Override
    public void delete(Long id) throws NoPermissionToDelete {
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm đươc khóa học với id cung cấp")));
        } else {
            throw new NoPermissionToDelete("Bạn không có quyền xóa khóa Bài học");
        }
    }

    @Override
    public Page<Lesson> findAll(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 1, pageable.getSort());
        Page<Lesson> lessons = lessonRepository.findAll(pageable);

        return lessons.map(a -> Lesson.builder()
                .id(a.getId())
                .courses(a.getCourses())
                .time(a.getTime())
                .moduleCourse(a.getModuleCourse())
                .sessionName(a.getSessionName())
                .build());
    }
}
