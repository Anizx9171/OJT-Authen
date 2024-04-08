package com.rikkei.managementuser.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AttendanceRequest {
    @NotNull
    @Min(1)
    private Long classId;
    @NotNull
    @Min(1)
    private Long lessonId;
}
