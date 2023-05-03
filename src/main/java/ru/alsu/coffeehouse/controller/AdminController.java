package ru.alsu.coffeehouse.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alsu.coffeehouse.service.OrderService;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final OrderService orderService;

    @GetMapping("/products")
    public String admin() {
        return "admin_products";
    }

    @GetMapping("/users")
    public String users() {
        return "admin_users";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAll());
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

    @GetMapping("/orders/{id}")
    public String order(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", orderService.getById(id));
        return "admin_order";
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable("id") int id) {
        orderService.deleteById(id);
        return "redirect:/admin/orders";
    }
}
