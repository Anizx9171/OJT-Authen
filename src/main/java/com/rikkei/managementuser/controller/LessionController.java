package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.request.LessonRequest;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.service.ILessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user-management/api/lession")
@RestController
@RequiredArgsConstructor
public class LessionController {
    private final ILessonService lessonService;
    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody LessonRequest lessonRequest) throws ModuleIsNotPartOfTheCourseException, ModuleCourseDoesNotExistException, ClassNotFoundException {
        lessonService.save(lessonRequest);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Thêm mới thành công"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editClass(@Valid @RequestBody LessonRequest lessonRequest, @PathVariable Long id) throws ModuleIsNotPartOfTheCourseException, LessonExistException, ModuleCourseDoesNotExistException, ClassNotFoundException {
        lessonService.edit(lessonRequest, id);
        return ResponseEntity.status(201).body(new ResponseAPI(true, "Bài học đă thay đổi thàng công"));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) throws NoPermissionToDelete {
        lessonService.delete(id);
        return ResponseEntity.status(204).body(null);
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable){
        return ResponseEntity.status(200).body(lessonService.findAll(pageable));
    }
}
