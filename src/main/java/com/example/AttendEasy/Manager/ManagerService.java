package com.example.AttendEasy.Manager;

import com.example.AttendEasy.Employee.Employee;
import com.example.AttendEasy.exceptions.AccessDeniedException;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;

public interface ManagerService {


    String generateEmployeeReport(long managerId, long employeeId) throws EmployeeNotFoundException, AccessDeniedException;

    String generateAllEmployeesReport(long managerId);

    void addEmployee(Employee employee);

}
