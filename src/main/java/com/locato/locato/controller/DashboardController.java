package com.locato.locato.controller;

import com.locato.locato.model.Venue;
import com.locato.locato.repository.VenueRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

        // Add to model
        model.addAttribute("userName", userName);
        model.addAttribute("userLocation", userLocation);
        model.addAttribute("venues", venues);

        return "dashboard";
    }
}
