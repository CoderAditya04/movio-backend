package com.movio.booking.service;

import com.movio.booking.entity.ShowSeat;
import com.movio.booking.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatLockService {

    private final ShowSeatRepository showSeatRepository;

    @Transactional
    public void lockSeats(List<Long> showSeatIds, Long userId) {
        for (Long id : showSeatIds) {
            ShowSeat seat = showSeatRepository.findByIdForUpdate(id)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + id));

            if (seat.getStatus() != ShowSeat.Status.AVAILABLE) {
                throw new IllegalStateException("Seat " + id + " is no longer available");
            }

            seat.setStatus(ShowSeat.Status.LOCKED);
            seat.setLockedByUserId(userId);
            seat.setLockedAt(LocalDateTime.now());
            showSeatRepository.save(seat);
        }
    }
}