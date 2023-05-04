package ru.alsu.coffeehouse.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alsu.coffeehouse.domain.dto.UserRegisterDTO;
import ru.alsu.coffeehouse.domain.model.Order;
import ru.alsu.coffeehouse.domain.model.Product;
import ru.alsu.coffeehouse.domain.model.User;
import ru.alsu.coffeehouse.mapper.UserMapper;
import ru.alsu.coffeehouse.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final ProductService productService;
    private final OrderService orderService;

    /**
     * Сохранение пользователя
     */
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Поиск пользователя по name
     *
     * @param username
     * @return
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Поиск пользователя по id
     */
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }


    /**
     * Регистрация пользователя
     *
     * @param userRegisterDTO
     */
    public void register(UserRegisterDTO userRegisterDTO) {
        save(userMapper.registerDTOToUser(userRegisterDTO));
    }

    public boolean addToCartByProductId(int id) {
        var user = userRepository.findById(authService.getAuthUser().orElseThrow().getId()).orElseThrow();

        // Проверяем, есть ли уже товар в корзине
        var product = user.getProducts().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        if (product.isEmpty()) {
            // Если нет, то добавляем
            var productToAdd = productService.getById(id);
            user.getProducts().add(productToAdd);
            save(user);
        }
        return true;
    }

    public List<Product> getCart() {
        return userRepository.findById(authService.getAuthUser().orElseThrow().getId()).orElseThrow().getProducts();
    }

    public void removeFromCartByProductId(int id) {
        var user = userRepository.findById(authService.getAuthUser().orElseThrow().getId()).orElseThrow();
        user.getProducts().removeIf(p -> p.getId() == id);
        save(user);
    }

    public double getCartTotalPrice() {
        return getCart().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Transactional
    public Order order() {
        var user = userRepository.findById(authService.getAuthUser().orElseThrow().getId()).orElseThrow();
        var order = new Order();
        double totalPrice = 0;
        for (Product product : user.getProducts()) {
            var pr = productService.getById(product.getId());
            totalPrice += pr.getPrice();
            order.getProducts().add(pr);
        }
        order.setUser(user);
        order.setTotal(totalPrice);
        order.setDate(java.time.LocalDateTime.now());
        order.setStatus("В обработке");

        // Удаляем продукты из корзины
        user.getProducts().clear();

        save(user);
        orderService.save(order);
        return order;
    }

    public Order getOrderById(int id) {
        var user = authService.getAuthUser().orElseThrow();
        var order = orderService.getById(id);

        if (order == null) {
            return null;
        }

        if (order.getUser().getId() == user.getId()) {
            return order;
        } else {
            return null;
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

}