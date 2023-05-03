package ru.alsu.coffeehouse.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alsu.coffeehouse.domain.model.Order;
import ru.alsu.coffeehouse.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Order getById(Integer id) {
        return findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
