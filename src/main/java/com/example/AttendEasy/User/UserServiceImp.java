package com.example.AttendEasy.User;


import com.example.AttendEasy.Times.Times;
import com.example.AttendEasy.Times.TimesRepository;
import com.example.AttendEasy.User.UserRequest.UserRequest;
import com.example.AttendEasy.User.UserResponse.UserResponse;
import com.example.AttendEasy.exceptions.AccessDeniedException;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.exceptions.TimeMissmatchException;
import com.example.AttendEasy.utilities.CustomDuration;
import com.example.AttendEasy.utilities.ReportResponse;
import com.example.AttendEasy.utilities.SessionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final TimesRepository timesRepository;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, TimesRepository timesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.timesRepository = timesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void checkIn(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException {
        Optional<User> found = userRepository.findById(employeeId);
        if (found.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        Optional<String> lastActivity = timesRepository.getLatestActivity(employeeId);
        if (lastActivity.isPresent() && lastActivity.get().split(",")[1].equalsIgnoreCase("IN")) {
            throw new TimeMissmatchException("The employee must Check Out before Checking In.");
        }

        Times checkIn = new Times();
        Date currentTime = new Date();
        checkIn.setUser(found.get());
        checkIn.setInTime(currentTime);

        timesRepository.save(checkIn);

    }

    @Override
    public void checkOut(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException {
        Optional<User> employeeFound = userRepository.findById(employeeId);
        if (employeeFound.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        Optional<String> lastActivity = timesRepository.getLatestActivity(employeeId);
        if ((lastActivity.isPresent() && !lastActivity.get().split(",")[1].equalsIgnoreCase("IN"))) {
            throw new TimeMissmatchException("The employee must Check In before Checking Out.");
        }

        Optional<Times> timeFound = timesRepository.getLastCheckIn(employeeId);
        if (timeFound.isEmpty()) {
            throw new TimeMissmatchException();
        }
        Times time = timeFound.get();
        Date currentTime = new Date();
        time.setUser(employeeFound.get());
        time.setOutTime(currentTime);

        timesRepository.save(time);

    }

    @Override
    public ReportResponse generateReportForEmployee(long employeeId, int month, int year) throws EmployeeNotFoundException, TimeMissmatchException {
        Optional<User> employeeFound = userRepository.findById(employeeId);
        if (employeeFound.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        if (month > 12 || year > new Date().getYear())
            throw new TimeMissmatchException("The entered date is not correct");
        User user = employeeFound.get();
        List<Times> timesByDate = getTimesByDate(user.getCheckTimes(), month, year);
        List<SessionResponse> sessions = getEmployeeSessions(timesByDate);
        Duration totalDuration = getTotalDuration(user.getCheckTimes());
        return new ReportResponse(sessions, CustomDuration.formatTime(totalDuration), employeeId);
    }

    @Override
    public ReportResponse generateReportForEmployee(long employeeId, long managerId) throws EmployeeNotFoundException, AccessDeniedException, TimeMissmatchException {
        Optional<User> managerFound = userRepository.findById(managerId);
        if (managerFound.isEmpty())
            throw new EmployeeNotFoundException("This manager does not exist.");

        Optional<User> employeeFound = userRepository.findById(employeeId);
        if (employeeFound.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        if (!managerFound.get().getUserList().contains(employeeFound.get()))
            throw new AccessDeniedException("This employee does not exist.");

        return generateReportForEmployee(employeeId,new Date().getMonth(), new Date().getYear());
    }

    @Override
    public List<ReportResponse> generateReportForAllEmployees(long managerId) throws EmployeeNotFoundException {
        Optional<User> managerFound = userRepository.findById(managerId);
        if (managerFound.isEmpty())
            throw new EmployeeNotFoundException("This manager does not exist.");

        User manager = managerFound.get();
        List<User> userList = manager.getUserList();
        List<ReportResponse> reportResponses = new ArrayList<>();
        userList.forEach(employee -> {
            List<Times> timesByDate = getTimesByDate(employee.getCheckTimes(), new Date().getMonth(), new Date().getYear());
            List<SessionResponse> sessions = getEmployeeSessions(timesByDate);
            Duration totalDuration = getTotalDuration(employee.getCheckTimes());
            reportResponses.add(new ReportResponse(sessions, CustomDuration.formatTime(totalDuration), employee.getId()));
        });
        return reportResponses;
    }

    @Override
    public UserResponse getEmployee(long employeeId) throws EmployeeNotFoundException {
        Optional<User> employeeFound = userRepository.findById(employeeId);
        if (employeeFound.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");
        User employee= employeeFound.get();
        UserResponse user = new UserResponse(employee.getFirstName(),
                employee.getLastName()
                ,employee.getMobileNumber()
                , employee.getEmail()
                , employee.getRoles());
        return user;
    }

    @Override
    public void addUser(UserRequest userReq) throws EmployeeNotFoundException {
        User user = new User();
        user.setEmail(userReq.getEmail());
        user.setFirstName(userReq.getFirstName());
        user.setLastName(userReq.getLastName());
        user.setMobileNumber(userReq.getMobileNumber());
        user.setRoles(userReq.getRoles());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        Optional<User> foundUser = userRepository.getUserByMobileNumber(userReq.getMobileNumber());
        if (foundUser.isPresent())
            throw new EmployeeNotFoundException("The mobile is used");

        if (userReq.getManagerId() != null) {
            Optional<User> manager = userRepository.findById(Long.parseLong(userReq.getManagerId()));
            manager.ifPresent(user::setManager);
        }

        userRepository.save(user);
    }

    @Override
    public long getIdByMobile(String mobile) throws EmployeeNotFoundException {
        Optional<User> userFound = userRepository.getUserByMobileNumber(mobile);
        if (userFound.isEmpty()){
            throw new EmployeeNotFoundException("Not Found");
        }
        return userFound.get().getId();
    }

    private List<Times> getTimesByDate(List<Times> times, int month, int year) {
        List<Times> newTimeList = new ArrayList<>();
        Date currentDate = new Date();
        times.forEach(time -> {
            if (currentDate.getMonth() == month && currentDate.getYear() == year)
                newTimeList.add(time);
        });
        return newTimeList;
    }

    private List<SessionResponse> getEmployeeSessions(List<Times> times) {
        final List<SessionResponse> sessionList = new ArrayList<>();
        times.forEach((time -> {
            if (time.getOutTime() != null) {
                Duration duration = getTimeBetweenChecks(time);
                SessionResponse session = new SessionResponse(CustomDuration.formatDate(time.getInTime()), CustomDuration.formatDate(time.getOutTime()), CustomDuration.formatTime(duration));
                sessionList.add(session);
            }
        }));
        return sessionList;
    }

    private Duration getTotalDuration(List<Times> times) {
        final Duration[] totalDuration = {Duration.ZERO};

        times.forEach(time -> {
            if (time.getOutTime() != null){
                Duration duration = getTimeBetweenChecks(time);
                totalDuration[0] = totalDuration[0].plus(duration);
            }
        });

        return totalDuration[0];
    }


    private Duration getTimeBetweenChecks(Times time) {
        Instant startInstant = time.getInTime().toInstant();
        Instant endInstant = time.getOutTime().toInstant();

        return Duration.between(startInstant, endInstant);
    }
}
