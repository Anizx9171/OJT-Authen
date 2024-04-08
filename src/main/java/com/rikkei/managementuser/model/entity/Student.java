package com.rikkei.managementuser.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "PhoneNumber", nullable = false, unique = true)
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b")
    private String phoneNumber;

    @Column(name = "Email", nullable = false, unique = true)
    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @Column(name = "DateOfBirth")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name = "Status")
    private int status;

    @Column(name = "CreatedAt")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date created_at;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "UpdateAt")
    private Date updated_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ClassId")
    private Class aClass;


}
