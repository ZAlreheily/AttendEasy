package com.example.AttendEasy.Times;


import com.example.AttendEasy.Employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "Times")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="times")
public class Times {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private String type;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


}
