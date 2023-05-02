package ru.alsu.coffeehouse.controller;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.alsu.coffeehouse.service.ProductService;
import ru.alsu.coffeehouse.service.UserService;

@Controller
@AllArgsConstructor
public class CartController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("products", userService.getCart());
        model.addAttribute("totalPrice", userService.getCartTotalPrice());
        return "cart";
    }

    @GetMapping("/order/{id}")
    public String order(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", userService.getOrderById(id));
        return "order";
    }

    @PostMapping("/cart/add-product/{id}")
    public String addProductToCart(@PathVariable("id") int id) {
        if (userService.addToCartByProductId(id)) {
            return "redirect:/cart";
        } else {
            return "redirect:?error";
        }
    }

    @PostMapping("/cart/remove-product/{id}")
    public String removeProductFromCart(@PathVariable("id") int id) {
        userService.removeFromCartByProductId(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/create-order")
    public String orderCreate() {
        var order = userService.order();
        return "redirect:/order/" + order.getId();
    }
}
