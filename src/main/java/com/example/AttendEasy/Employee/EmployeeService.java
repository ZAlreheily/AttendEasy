package com.example.AttendEasy.Employee;


import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.exceptions.TimeMissmatchException;
import com.example.AttendEasy.utilities.ReportResponse;
import com.example.AttendEasy.utilities.SessionResponse;

public interface EmployeeService {

    void checkIn(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException;

    void checkOut(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException;

    ReportResponse generateReportForEmployee(long employeeId, int month, int year);
}
