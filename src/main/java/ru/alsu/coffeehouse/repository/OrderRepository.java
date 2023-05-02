package ru.alsu.coffeehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alsu.coffeehouse.domain.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
