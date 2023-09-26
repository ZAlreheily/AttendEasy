package com.example.AttendEasy.User;


import com.example.AttendEasy.User.UserRequest.UserRequest;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.exceptions.TimeMissmatchException;
import com.example.AttendEasy.utilities.ReportResponse;

import java.util.List;

public interface UserService {

    void checkIn(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException;

    void checkOut(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException;

    ReportResponse generateReportForEmployee(long employeeId, int month, int year) throws EmployeeNotFoundException, TimeMissmatchException;

    List<ReportResponse> generateReportForAllEmployees(long managerId, int month, int year) throws EmployeeNotFoundException, TimeMissmatchException;

    User getEmployee(long employeeId) throws EmployeeNotFoundException;


    void addUser(UserRequest userReq) throws EmployeeNotFoundException;
}
