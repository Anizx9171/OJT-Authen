package com.rikkei.managementuser.service;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.LessonRequest;
import com.rikkei.managementuser.model.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILessonService {
    void save(LessonRequest lessonRequest) throws  ModuleCourseDoesNotExistException, ModuleIsNotPartOfTheCourseException, ClassNotFoundException;

    void edit(LessonRequest lessonRequest, Long id) throws LessonExistException, ModuleCourseDoesNotExistException, ModuleIsNotPartOfTheCourseException, ClassNotFoundException;

    Lesson findById(Long id);

    void delete(Long id) throws NoPermissionToDelete;
    Page<Lesson> findAll(Pageable pageable);
}
