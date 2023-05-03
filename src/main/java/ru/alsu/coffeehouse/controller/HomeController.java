package ru.alsu.coffeehouse.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alsu.coffeehouse.service.AuthService;
import ru.alsu.coffeehouse.service.ProductService;

@Controller
@AllArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final AuthService authService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("authUser", authService.getAuthUserOrNull());

        return "index";
    }
}
