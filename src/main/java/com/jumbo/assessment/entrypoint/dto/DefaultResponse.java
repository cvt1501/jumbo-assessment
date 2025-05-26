package com.jumbo.assessment.entrypoint.dto;

public record DefaultResponse<T>(String message, T data) {
}
