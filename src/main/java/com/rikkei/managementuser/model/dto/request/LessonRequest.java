package com.rikkei.managementuser.model.dto.request;

import com.rikkei.managementuser.model.entity.Courses;
import com.rikkei.managementuser.model.entity.ModuleCourse;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequest {
    @Min(1)
    @NonNull
    private Long coursesId;

    @Min(1)
    @NonNull
    private Long moduleCourseId;

    @Length(max = 100, min = 3)
    private String sessionName;

    @Min(1)
    @NonNull
    private Integer time;
}
