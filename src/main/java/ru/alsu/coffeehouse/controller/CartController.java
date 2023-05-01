package ru.alsu.coffeehouse.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CartController {

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/order")
    public String order() {
        return "order";
    }
}
