package ru.alsu.coffeehouse.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Покупатель
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Список товаров
     */
    @ManyToMany
    @JoinTable(
            name = "users_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    /**
     * Статус заказа
     */
    @Column(name = "status")
    private String status;

    /**
     * Сумма заказа
     */
    @Column(name = "total")
    private double total;

    /**
     * Дата заказа
     */
    @Column(name = "date")
    private String date;
}
