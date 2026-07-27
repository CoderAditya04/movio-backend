package com.movio.booking.service;

import com.movio.booking.dto.CreateShowRequest;
import com.movio.booking.entity.*;
import com.movio.booking.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final SeatRepository seatRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    @Transactional
    public Show createShow(CreateShowRequest req) {
        Movie movie = movieRepository.findById(req.movieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Screen screen = screenRepository.findById(req.screenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(req.startTime());
        show.setEndTime(req.endTime());
        show.setBasePrice(req.basePrice());
        show = showRepository.save(show);

        generateShowSeats(show, screen);
        return show;
    }

    private void generateShowSeats(Show show, Screen screen) {
        List<Seat> seats = seatRepository.findByScreenId(screen.getId());
        List<ShowSeat> showSeats = new ArrayList<>();

        for (Seat seat : seats) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setStatus(ShowSeat.Status.AVAILABLE);
            // premium seats cost more than base price — simple multiplier for now
            double price = seat.getSeatType() == Seat.SeatType.PREMIUM
                    ? show.getBasePrice() * 1.5
                    : show.getBasePrice();
            showSeat.setPrice(price);
            showSeats.add(showSeat);
        }
        showSeatRepository.saveAll(showSeats);
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public List<ShowSeat> getSeatsForShow(Long showId) {
        return showSeatRepository.findByShowId(showId);
    }
}