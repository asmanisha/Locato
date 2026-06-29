package com.locato.locato.repository;

import com.locato.locato.model.Venue;
import com.locato.locato.enums.VenueCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    // Find venues by location
    List<Venue> findByLocation(String location);

    // Find venues by category
    List<Venue> findByCategory(VenueCategory category);

    // Find venues by location and category
    List<Venue> findByLocationAndCategory(String location, VenueCategory category);

    // Find venues by location and category with rating > given value
    List<Venue> findByLocationAndCategoryAndRatingGreaterThan(String location, VenueCategory category, Double rating);
}