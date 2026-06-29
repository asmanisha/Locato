package com.locato.locato.repository;

import com.locato.locato.model.ShowTime;
import com.locato.locato.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    // Find show times by venue
    List<ShowTime> findByVenue(Venue venue);

    // Find show times by venue and date
    List<ShowTime> findByVenueAndShowTimeBetween(Venue venue, LocalDateTime start, LocalDateTime end);
}
