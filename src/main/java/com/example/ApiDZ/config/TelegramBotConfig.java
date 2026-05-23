package com.example.ApiDZ.config;

import com.example.ApiDZ.bot.RecommendationBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramBotConfig implements ApplicationRunner {

    private final RecommendationBot recommendationBot;

    @Override
    public void run(ApplicationArguments args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(recommendationBot);
        log.info("Telegram-бот @{} зарегистрирован", recommendationBot.getBotUsername());
    }
}
