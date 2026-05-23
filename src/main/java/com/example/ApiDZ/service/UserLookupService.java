package com.example.ApiDZ.service;

import com.example.ApiDZ.domain.main.User;
import com.example.ApiDZ.repository.main.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLookupService {

    private final UserRepository userRepository;

    @Transactional(value = "mainTransactionManager", readOnly = true)
    public Optional<User> findUniqueByUsername(String username) {
        String login = username == null ? "" : username.trim();
        List<User> users = userRepository.findByUsernameIgnoreCase(login);
        if (users.size() != 1) {
            log.info("Поиск пользователя '{}': найдено {} записей", login, users.size());
            return Optional.empty();
        }
        return Optional.of(users.getFirst());
    }
}
