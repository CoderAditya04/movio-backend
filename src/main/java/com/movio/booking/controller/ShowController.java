package com.movio.booking.controller;

import com.movio.booking.dto.CreateShowRequest;
import com.movio.booking.entity.Show;
import com.movio.booking.entity.ShowSeat;
import com.movio.booking.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/admin/shows")
    public Show createShow(@RequestBody CreateShowRequest req) {
        return showService.createShow(req);
    }

    @GetMapping("/api/shows/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable Long movieId) {
        return showService.getShowsByMovie(movieId);
    }

    @GetMapping("/api/shows/{showId}/seats")
    public List<ShowSeat> getSeatsForShow(@PathVariable Long showId) {
        return showService.getSeatsForShow(showId);
    }
}
