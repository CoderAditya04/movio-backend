package com.movio.booking.dto;

public record CreateScreenRequest(Long theatreId, String name, Integer totalRows, Integer totalColumns) {}
