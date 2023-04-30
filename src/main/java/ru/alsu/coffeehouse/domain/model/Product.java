package ru.alsu.coffeehouse.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    /*
     * Цена товара по скидке
     */
    @Column(name = "discount_price")
    private double discountPrice;

    /*
     * Есть ли скидка на товар
     */
    @Column(name = "discount")
    private boolean discount;

    /*
     * Картинка товара
     */
    @Column(name = "image")
    private String image;

}