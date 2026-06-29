package com.locato.locato.controller;

import com.locato.locato.dto.CartItemDTO;
import com.locato.locato.model.MenuItem;
import com.locato.locato.repository.MenuItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private MenuItemRepository menuItemRepository;

    // View cart page
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/";
        }

        // Get cart from session
        Map<Long, CartItemDTO> cart = getCartFromSession(session);
        List<CartItemDTO> cartItems = new ArrayList<>(cart.values());

        // Calculate total
        double total = cartItems.stream()
                .mapToDouble(CartItemDTO::getTotalPrice)
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("itemCount", cartItems.size());

        return "cart";
    }

    // Add item to cart (AJAX)
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestParam Long menuItemId,
            @RequestParam(defaultValue = "1") Integer quantity,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Get cart from session
            Map<Long, CartItemDTO> cart = getCartFromSession(session);

            // Get menu item
            Optional<MenuItem> menuItemOptional = menuItemRepository.findById(menuItemId);
            if (menuItemOptional.isEmpty()) {
                response.put("success", false);
                response.put("message", "Menu item not found");
                return ResponseEntity.badRequest().body(response);
            }

            MenuItem menuItem = menuItemOptional.get();

            // Check if item already in cart
            if (cart.containsKey(menuItemId)) {
                CartItemDTO existingItem = cart.get(menuItemId);
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
            } else {
                CartItemDTO newItem = new CartItemDTO(
                        menuItemId,
                        menuItem.getItemName(),
                        menuItem.getPrice(),
                        quantity
                );
                cart.put(menuItemId, newItem);
            }

            // Save cart back to session
            session.setAttribute("cart", cart);

            // Calculate cart count
            int totalItems = cart.values().stream()
                    .mapToInt(CartItemDTO::getQuantity)
                    .sum();

            response.put("success", true);
            response.put("message", "Item added to cart");
            response.put("cartCount", totalItems);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error adding to cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Update cart item quantity
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateCartItem(
            @RequestParam Long menuItemId,
            @RequestParam Integer quantity,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            Map<Long, CartItemDTO> cart = getCartFromSession(session);

            if (cart.containsKey(menuItemId)) {
                if (quantity <= 0) {
                    cart.remove(menuItemId);
                } else {
                    CartItemDTO item = cart.get(menuItemId);
                    item.setQuantity(quantity);
                }

                session.setAttribute("cart", cart);

                // Calculate new totals
                List<CartItemDTO> cartItems = new ArrayList<>(cart.values());
                double total = cartItems.stream()
                        .mapToDouble(CartItemDTO::getTotalPrice)
                        .sum();
                int totalItems = cartItems.stream()
                        .mapToInt(CartItemDTO::getQuantity)
                        .sum();

                response.put("success", true);
                response.put("cartCount", totalItems);
                response.put("total", total);
                response.put("itemTotal", cart.containsKey(menuItemId) ?
                        cart.get(menuItemId).getTotalPrice() : 0);
            } else {
                response.put("success", false);
                response.put("message", "Item not found in cart");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Remove item from cart
    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFromCart(
            @RequestParam Long menuItemId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            Map<Long, CartItemDTO> cart = getCartFromSession(session);
            cart.remove(menuItemId);
            session.setAttribute("cart", cart);

            // Calculate new totals
            List<CartItemDTO> cartItems = new ArrayList<>(cart.values());
            double total = cartItems.stream()
                    .mapToDouble(CartItemDTO::getTotalPrice)
                    .sum();
            int totalItems = cartItems.stream()
                    .mapToInt(CartItemDTO::getQuantity)
                    .sum();

            response.put("success", true);
            response.put("cartCount", totalItems);
            response.put("total", total);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error removing from cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Clear cart
    @PostMapping("/clear")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> clearCart(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            session.removeAttribute("cart");
            response.put("success", true);
            response.put("message", "Cart cleared");
            response.put("cartCount", 0);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error clearing cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get cart count (for AJAX)
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Integer> getCartCount(HttpSession session) {
        Map<Long, CartItemDTO> cart = getCartFromSession(session);
        int totalItems = cart.values().stream()
                .mapToInt(CartItemDTO::getQuantity)
                .sum();
        return ResponseEntity.ok(totalItems);
    }

    // Helper method to get cart from session
    @SuppressWarnings("unchecked")
    private Map<Long, CartItemDTO> getCartFromSession(HttpSession session) {
        Map<Long, CartItemDTO> cart = (Map<Long, CartItemDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
