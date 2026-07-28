package com.movio.booking.service;

import com.movio.booking.entity.ShowSeat;
import com.movio.booking.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatLockExpiryJob {

    private final ShowSeatRepository showSeatRepository;

    private static final int LOCK_DURATION_MINUTES = 5;

    @Scheduled(fixedRate = 60000) // runs every 60 seconds
    @Transactional
    public void releaseExpiredLocks() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(LOCK_DURATION_MINUTES);
        List<ShowSeat> expiredSeats = showSeatRepository.findByStatusAndLockedAtBefore(
                ShowSeat.Status.LOCKED, cutoff);

        for (ShowSeat seat : expiredSeats) {
            seat.setStatus(ShowSeat.Status.AVAILABLE);
            seat.setLockedByUserId(null);
            seat.setLockedAt(null);
        }
        showSeatRepository.saveAll(expiredSeats);

        if (!expiredSeats.isEmpty()) {
            System.out.println("Released " + expiredSeats.size() + " expired seat locks");
        }
    }
}