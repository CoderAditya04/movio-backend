package com.movio.booking.dto;

import java.time.LocalDateTime;

public record CreateShowRequest(Long movieId, Long screenId, LocalDateTime startTime, LocalDateTime endTime, Double basePrice) {}
