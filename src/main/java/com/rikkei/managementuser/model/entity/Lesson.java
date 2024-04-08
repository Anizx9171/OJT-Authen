package com.rikkei.managementuser.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courses_id", nullable = false)
    private Courses courses;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id", nullable = false)
    private ModuleCourse moduleCourse;
    @Column(name="session_name", nullable = false, unique = true)
    private String sessionName;
    @Column(name = "time")
    private int time;
}
