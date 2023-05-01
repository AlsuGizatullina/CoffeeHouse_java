package ru.alsu.coffeehouse.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/products")
    public String admin() {
        return "admin_products";
    }

    @GetMapping("/users")
    public String users() {
        return "admin_users";
    }

    @GetMapping("/orders")
    public String orders() {
        return "admin_orders";
    }

    @GetMapping("/redact/product")
    public String redactProducts() {
        return "admin_redact_product";
    }

    @GetMapping("/redact/user")
    public String redactUsers() {
        return "admin_redact_user";
    }

}
