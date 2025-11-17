package com.example.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    private Date timestamp;
    
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data, new Date());
    }
    
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null, new Date());
    }
}