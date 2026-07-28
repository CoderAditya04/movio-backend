package com.movio.booking.dto;

import java.util.List;

public record CreateBookingRequest(Long showId, List<Long> showSeatIds) {}
