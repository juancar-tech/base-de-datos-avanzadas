package com.estaciones.application.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(
    T data,
    String status,
    LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "SUCCESS", LocalDateTime.now());
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, "ERROR: " + message, LocalDateTime.now());
    }
}


