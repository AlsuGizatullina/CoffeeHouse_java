package ru.alsu.coffeehouse.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
@Transactional
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
        var user = authService.getAuthUser().orElseThrow();

        // Проверяем, есть ли уже товар в корзине
        var product = user.getProducts().stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        if (product.isPresent()) {
            return true;
        } else {
            // Если нет, то добавляем
            var productToAdd = productService.getById(id);
            user.getProducts().add(productToAdd);
            save(user);
            return true;
        }
    }

    public List<Product> getCart() {
        return authService.getAuthUser().orElseThrow().getProducts();
    }

    public void removeFromCartByProductId(int id) {
        var user = authService.getAuthUser().orElseThrow();
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
        order.setTotal(totalPrice);
        order.setUser(user);
        order.setDate(java.time.LocalDateTime.now());
        order.setStatus("В обработке");
        user.getProducts().clear();
        save(user);
        orderService.save(order);
        return order;
    }

    public Order getOrderById(int id) {
        var user = authService.getAuthUser().orElseThrow();
        var order = orderService.getById(id);

        if (order.getUser().getId() == user.getId()) {
            return order;
        } else {
            return null;
        }
    }
}