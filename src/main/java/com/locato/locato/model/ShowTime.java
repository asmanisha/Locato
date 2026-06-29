package com.locato.locato.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "show_times")
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(name = "show_time", nullable = false)
    private LocalDateTime showTime;

    @Column(name = "screen_number")
    private String screenNumber;

    private Double price;

    // Constructors
    public ShowTime() {}

    public ShowTime(Venue venue, String movieName, LocalDateTime showTime, String screenNumber, Double price) {
        this.venue = venue;
        this.movieName = movieName;
        this.showTime = showTime;
        this.screenNumber = screenNumber;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public LocalDateTime getShowTime() { return showTime; }
    public void setShowTime(LocalDateTime showTime) { this.showTime = showTime; }

    public String getScreenNumber() { return screenNumber; }
    public void setScreenNumber(String screenNumber) { this.screenNumber = screenNumber; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
