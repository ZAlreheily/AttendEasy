package com.example.AttendEasy.Employee;

import com.example.AttendEasy.Manager.Manager;
import com.example.AttendEasy.Times.Times;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity(name = "Employee")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private long id;
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;
    @Column(
            name = "password",
            nullable = false
    )
    private String password;
    @Column(
            name = "mobile_number",
            nullable = false
    )
    private int mobileNumber;
    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "employee")
    private List<Times> checkTimes;


}
