package ru.alsu.coffeehouse.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alsu.coffeehouse.domain.model.Product;
import ru.alsu.coffeehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Получение всех продуктов
     * @return
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Получение конкретного продукта
     * @return
     */
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    /**
     * Получение конкретного продукта
     * @return
     */
    public Product getById(int id) {
        return findById(id).orElseThrow();
    }
}