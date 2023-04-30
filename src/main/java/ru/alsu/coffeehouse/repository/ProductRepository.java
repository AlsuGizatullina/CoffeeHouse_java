package ru.alsu.coffeehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alsu.coffeehouse.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
