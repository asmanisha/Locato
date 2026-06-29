package com.locato.locato.controller;

import com.locato.locato.model.User;
import com.locato.locato.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    // Show login page
    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    // Handle login
    @PostMapping("/login")
    public String login(@RequestParam String name,
                        @RequestParam String location,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        // Validate input
        if (name == null || name.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter your name");
            return "redirect:/";
        }

        if (location == null || location.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a location");
            return "redirect:/";
        }

        // Check if user exists, if not create new user
        User user = userRepository.findByName(name.trim());
        if (user == null) {
            user = new User(name.trim(), location);
            userRepository.save(user);
        } else {
            // Update location if changed
            user.setLocation(location);
            userRepository.save(user);
        }

        // Store user in session
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userLocation", user.getLocation());

        return "redirect:/dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

