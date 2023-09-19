package com.example.AttendEasy.Manager;

import com.example.AttendEasy.Employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "manager")
public class Manager {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private long id;

    @OneToMany(mappedBy = "manager")
    private List<Employee> employeeList;


}
