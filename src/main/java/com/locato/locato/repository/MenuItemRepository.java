package com.locato.locato.repository;

import com.locato.locato.model.MenuItem;
import com.locato.locato.model.Venue;
import com.locato.locato.enums.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // Find menu items by venue
    List<MenuItem> findByVenue(Venue venue);

    // Find menu items by venue and item type
    List<MenuItem> findByVenueAndItemType(Venue venue, MenuType itemType);

    // Find menu items by venue and search by name (for search feature)
    List<MenuItem> findByVenueAndItemNameContainingIgnoreCase(Venue venue, String searchTerm);

    // Find menu items by venue and price less than
    List<MenuItem> findByVenueAndPriceLessThan(Venue venue, Double price);
}
