package com.example.ApiDZ.bot;

import com.example.ApiDZ.domain.main.User;
import com.example.ApiDZ.service.RecommendationService;
import com.example.ApiDZ.service.UserLookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RecommendationBot extends TelegramLongPollingBot {

    private static final String USER_NOT_FOUND = "Пользователь не найден";
    private static final String HELP_TEXT = """
            Добро пожаловать! Я бот рекомендаций.

            Команда:
            /recommend <логин>

            <логин> - поле username из таблицы users в базе знаний банка.
            Это не ник в Telegram и не имя с паспорта.

            Примеры (тестовые данные):
            /recommend ivan - без рекомендаций
            /recommend maria - есть рекомендации
            """;

    private final String botUsername;
    private final UserLookupService userLookupService;
    private final RecommendationService recommendationService;

    public RecommendationBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            UserLookupService userLookupService,
            RecommendationService recommendationService) {
        super(botToken);
        this.botUsername = botUsername;
        this.userLookupService = userLookupService;
        this.recommendationService = recommendationService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText().trim();
        log.debug("Сообщение от chatId={}: {}", chatId, text);

        if (text.startsWith("/recommend")) {
            handleRecommend(chatId, text);
            return;
        }

        if (text.startsWith("/start") || text.startsWith("/help")) {
            sendText(chatId, HELP_TEXT);
            return;
        }

        sendText(chatId, HELP_TEXT);
    }

    private void handleRecommend(String chatId, String text) {
        String username = extractUsername(text);
        if (username == null || username.isBlank()) {
            sendText(chatId, USER_NOT_FOUND);
            return;
        }

        Optional<User> user = userLookupService.findUniqueByUsername(username);
        if (user.isEmpty()) {
            sendText(chatId, USER_NOT_FOUND);
            return;
        }

        User found = user.get();
        List<String> recommendations = recommendationService.getRecommendations(found.getId());
        sendText(chatId, formatRecommendations(found, recommendations));
    }

    private String extractUsername(String text) {
        String command = text.split("\\s+", 2)[0];
        if (command.contains("@")) {
            command = command.substring(0, command.indexOf('@'));
        }
        if (!"/recommend".equals(command)) {
            return null;
        }
        String[] parts = text.split("\\s+", 2);
        return parts.length < 2 ? null : parts[1].trim();
    }

    private String formatRecommendations(User user, List<String> recommendations) {
        StringBuilder message = new StringBuilder();
        message.append("Здравствуйте ").append(user.getFullName()).append("\n\n");
        message.append("Новые продукты для вас:\n");
        if (recommendations.isEmpty()) {
            message.append("- пока нет новых рекомендаций.");
        } else {
            for (String recommendation : recommendations) {
                message.append("• ").append(recommendation).append('\n');
            }
        }
        return message.toString().trim();
    }

    private void sendText(String chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить сообщение в Telegram (chatId={}): {}", chatId, e.getMessage(), e);
        }
    }
}
