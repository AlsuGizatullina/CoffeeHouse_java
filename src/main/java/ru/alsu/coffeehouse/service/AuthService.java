package ru.alsu.coffeehouse.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.alsu.coffeehouse.domain.model.User;
import ru.alsu.coffeehouse.repository.UserRepository;
import ru.alsu.coffeehouse.security.SecurityUser;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    /**
     * Получение авторизованного пользователя
     * @return
     */
    public Optional<User> getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.of(((SecurityUser) authentication.getPrincipal()).getUser());
    }

    /**
     * Получение авторизованного пользователя
     * @return
     */
    public User getAuthUserOrNull() {
        var authUser = getAuthUser().orElse(null);
        if (authUser == null) {
            return null;
        } else {
            return userRepository.findById(authUser.getId()).orElse(null);
        }
    }
}