package com.example.AttendEasy.Employee;


import com.example.AttendEasy.Times.Times;
import com.example.AttendEasy.Times.TimesRepository;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.exceptions.TimeMissmatchException;
import com.example.AttendEasy.utilities.ReportResponse;
import com.example.AttendEasy.utilities.SessionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImp implements EmployeeService {

    EmployeeRepository employeeRepository;

    TimesRepository timesRepository;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeRepository, TimesRepository timesRepository) {
        this.employeeRepository = employeeRepository;
        this.timesRepository = timesRepository;
    }


    @Override
    public void checkIn(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException {
        Optional<Employee> found = employeeRepository.findById(employeeId);
        if (found.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        Optional<String> lastActivity = timesRepository.getLatestActivity(employeeId);
        if (lastActivity.isPresent() && lastActivity.get().equalsIgnoreCase("IN")) {
            throw new TimeMissmatchException("The employee must Check Out before Checking In.");
        }

        Times checkIn = new Times();
        Date currentTime = new Date();
        checkIn.setEmployee(found.get());
        checkIn.setInTime(currentTime);

        timesRepository.save(checkIn);

    }

    @Override
    public void checkOut(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException {
        Optional<Employee> employeeFound = employeeRepository.findById(employeeId);
        if (employeeFound.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        Optional<String> lastActivity = timesRepository.getLatestActivity(employeeId);
        if (!(lastActivity.isPresent() && lastActivity.get().equalsIgnoreCase("IN"))) {
            throw new TimeMissmatchException("The employee must Check In before Checking Out.");
        }

        Optional<Times> timeFound = timesRepository.getLastCheckIn(employeeId);
        if (timeFound.isEmpty()) {
            throw new TimeMissmatchException();
        }
        Times time = timeFound.get();
        Date currentTime = new Date();
        time.setEmployee(employeeFound.get());
        time.setOutTime(currentTime);

        timesRepository.save(time);

    }

    @Override
    public ReportResponse generateReportForEmployee(long employeeId, int month, int year) {

        return null;
    }

    private List<SessionResponse> getEmployeeSessions(List<Times> times) {
        final List<SessionResponse> sessionList = new ArrayList<SessionResponse>();
        times.forEach((time -> {
            Duration duration = getTimeBetweenChecks(time);
            SessionResponse session = new SessionResponse(time.getInTime(), time.getOutTime(), duration);
            sessionList.add(session);
        }));
        return sessionList;
    }

    private Duration getTotalDuration(List<Times> times) {
        final Duration[] totalDuration = {Duration.ZERO};

        times.forEach(time -> {
            Duration duration = getTimeBetweenChecks(time);
            totalDuration[0] = totalDuration[0].plus(duration);
        });

        return totalDuration[0];
    }


    private Duration getTimeBetweenChecks(Times time) {
        Instant startInstant = time.getInTime().toInstant();
        Instant endInstant = time.getOutTime().toInstant();

        return Duration.between(startInstant, endInstant);
    }
}
