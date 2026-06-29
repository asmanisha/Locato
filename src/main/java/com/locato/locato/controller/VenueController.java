package com.locato.locato.controller;

import com.locato.locato.enums.MenuType;
import com.locato.locato.model.MenuItem;
import com.locato.locato.model.Venue;
import com.locato.locato.repository.MenuItemRepository;
import com.locato.locato.repository.VenueRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/venue/{id}")
    public String viewVenueDetails(@PathVariable Long id,
                                   HttpSession session,
                                   Model model) {
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }

        Optional<Venue> venueOptional = venueRepository.findById(id);
        if (venueOptional.isEmpty()) {
            return "redirect:/dashboard";
        }

        Venue venue = venueOptional.get();

        // Get menu items if it's a cafeteria
        List<MenuItem> menuItems = null;
        if (venue.getCategory().name().equals("CAFETERIA")) {
            menuItems = menuItemRepository.findByVenue(venue);

            // Group menu items by type
            List<MenuItem> breakfast = menuItemRepository.findByVenueAndItemType(venue, MenuType.BREAKFAST);
            List<MenuItem> lunch = menuItemRepository.findByVenueAndItemType(venue, MenuType.LUNCH);
            List<MenuItem> beverages = menuItemRepository.findByVenueAndItemType(venue, MenuType.BEVERAGE);

            // Debug: Print counts
            System.out.println("=== MENU ITEMS FOR " + venue.getName() + " ===");
            System.out.println("Total menu items: " + (menuItems != null ? menuItems.size() : 0));
            System.out.println("Breakfast: " + (breakfast != null ? breakfast.size() : 0));
            System.out.println("Lunch: " + (lunch != null ? lunch.size() : 0));
            System.out.println("Beverages: " + (beverages != null ? beverages.size() : 0));

            model.addAttribute("breakfastItems", breakfast);
            model.addAttribute("lunchItems", lunch);
            model.addAttribute("beverageItems", beverages);
        }

        model.addAttribute("venue", venue);
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("isCafeteria", venue.getCategory().name().equals("CAFETERIA"));
        model.addAttribute("isHotel", venue.getCategory().name().equals("HOTEL"));
        model.addAttribute("isTheatre", venue.getCategory().name().equals("THEATRE"));

        return "venue-details";
    }
}