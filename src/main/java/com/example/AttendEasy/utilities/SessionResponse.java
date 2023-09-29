package com.example.AttendEasy.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.util.Date;

@Data
@AllArgsConstructor
public class SessionResponse {
    private String timeIn;
    private String timeOut;
    private String duration;
}
