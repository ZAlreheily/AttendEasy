package com.example.AttendEasy.Manager;

import com.example.AttendEasy.Employee.Employee;
import com.example.AttendEasy.Employee.EmployeeRepository;
import com.example.AttendEasy.Times.Times;
import com.example.AttendEasy.exceptions.AccessDeniedException;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class ManagerServiceImp implements ManagerService {

    ManagerRepository managerRepository;
    EmployeeRepository employeeRepository;


    public ManagerServiceImp(ManagerRepository managerRepository, EmployeeRepository employeeRepository) {
        this.managerRepository = managerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String generateEmployeeReport(long managerId, long employeeId) throws EmployeeNotFoundException, AccessDeniedException {
        Optional<Employee> found = employeeRepository.findById(employeeId);
        if (found.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        if (found.get().getManager().getId() != managerId)
            throw new AccessDeniedException("Access Denied for this employee's information.");

        List<Times> employeeTimes = found.get().getCheckTimes();
        String report = "";
        int totalWorkHours;
        employeeTimes.forEach(times -> {

//            totalWorkHours += times.
        });

        return null;
    }

    @Override
    public String generateAllEmployeesReport(long managerId) {
        return null;
    }

    @Override
    public void addEmployee(Employee employee) {

    }
}
