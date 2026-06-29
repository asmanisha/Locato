package com.locato.locato.repository;

import com.locato.locato.model.Order;
import com.locato.locato.model.User;
import com.locato.locato.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find orders by user
    List<Order> findByUser(User user);

    // Find orders by status
    List<Order> findByStatus(OrderStatus status);

    // Find orders by user and status
    List<Order> findByUserAndStatus(User user, OrderStatus status);

    // Find order by payment ID
    Order findByPaymentId(String paymentId);
}
