package com.locato.locato.controller;

import com.locato.locato.enums.VenueCategory;
import com.locato.locato.model.Venue;
import com.locato.locato.repository.VenueRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private VenueRepository venueRepository;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }

        // Get user info from session
        String userName = (String) session.getAttribute("userName");
        String userLocation = (String) session.getAttribute("userLocation");

        // Get all venues for user's location
        List<Venue> venues = venueRepository.findByLocation(userLocation);

        // Calculate counts for each category
        long hotelCount = venues.stream().filter(v -> v.getCategory() == VenueCategory.HOTEL).count();
        long cafeteriaCount = venues.stream().filter(v -> v.getCategory() == VenueCategory.CAFETERIA).count();
        long theatreCount = venues.stream().filter(v -> v.getCategory() == VenueCategory.THEATRE).count();

        // Add to model
        model.addAttribute("userName", userName);
        model.addAttribute("userLocation", userLocation);
        model.addAttribute("venues", venues);
        model.addAttribute("allVenues", venues);
        model.addAttribute("hotelCount", hotelCount);
        model.addAttribute("cafeteriaCount", cafeteriaCount);
        model.addAttribute("theatreCount", theatreCount);
        model.addAttribute("venueCategories", VenueCategory.values());

        return "dashboard";
    }

    // AJAX endpoint to filter venues by category
    @GetMapping("/api/venues/filter")
    @ResponseBody
    public List<Venue> filterVenues(@RequestParam String location,
                                    @RequestParam(required = false) String category) {
        if (category == null || category.isEmpty() || category.equals("ALL")) {
            return venueRepository.findByLocation(location);
        }

        try {
            VenueCategory venueCategory = VenueCategory.valueOf(category);
            return venueRepository.findByLocationAndCategory(location, venueCategory);
        } catch (IllegalArgumentException e) {
            return venueRepository.findByLocation(location);
        }
    }

    // AJAX endpoint to change location
    @GetMapping("/api/venues/location")
    @ResponseBody
    public List<Venue> changeLocation(@RequestParam String location,
                                      @RequestParam(required = false) String category) {
        if (category == null || category.isEmpty() || category.equals("ALL")) {
            return venueRepository.findByLocation(location);
        }

        try {
            VenueCategory venueCategory = VenueCategory.valueOf(category);
            return venueRepository.findByLocationAndCategory(location, venueCategory);
        } catch (IllegalArgumentException e) {
            return venueRepository.findByLocation(location);
        }
    }

    // Update user location in session
    @GetMapping("/api/update-location")
    @ResponseBody
    public String updateLocation(@RequestParam String location, HttpSession session) {
        session.setAttribute("userLocation", location);
        return "Location updated to: " + location;
    }
}