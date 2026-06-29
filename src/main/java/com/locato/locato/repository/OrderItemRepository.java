package com.locato.locato.repository;

import com.locato.locato.model.OrderItem;
import com.locato.locato.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Find order items by order
    List<OrderItem> findByOrder(Order order);
}
