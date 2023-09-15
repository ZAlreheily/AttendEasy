package com.example.AttendEasy.Employee;


import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.exceptions.TimeMissmatchException;

public interface EmployeeService {

    void checkIn(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException;

    void checkOut(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException;
}
