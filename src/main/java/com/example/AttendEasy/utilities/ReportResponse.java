package com.example.AttendEasy.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
public class ReportResponse {
    private List<SessionResponse> sessions;
    private String duration;
    private long employeeId;

}
