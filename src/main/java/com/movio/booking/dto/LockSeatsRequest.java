package com.movio.booking.dto;

import java.util.List;

public record LockSeatsRequest(Long showId, List<Long> seatIds) {}