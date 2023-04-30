package ru.alsu.coffeehouse.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alsu.coffeehouse.domain.dto.UserRegisterDTO;
import ru.alsu.coffeehouse.domain.model.User;
import ru.alsu.coffeehouse.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final UserMapper userMapper;

    /**
     * Сохранение пользователя
     */
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Поиск пользователя по email
     * @param email
     * @return
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Поиск пользователя по id
     */
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }


    /**
     * Регистрация пользователя
     * @param userRegisterDTO
     */
//    public void register(UserRegisterDTO userRegisterDTO) {
//        save(userMapper.registerDTOToUser(userRegisterDTO));
//    }
}