# ApiDZ - сервис рекомендаций банковских продуктов

Spring Boot-приложение для выдачи персональных рекомендаций на основе базы знаний о клиентах, динамических правил и Telegram-бота.

## Стек

- Java 21, Spring Boot 4
- JPA, H2 (база знаний) / PostgreSQL (правила)
- Caffeine, Liquibase
- Telegram Bots API

## Быстрый старт

```bash
./mvnw package
export TELEGRAM_BOT_TOKEN=<ваш_токен>
java -jar target/ApiDZ-0.0.1-SNAPSHOT.jar
```

Для локальной проверки нужен PostgreSQL с БД `rules_db`. Тестовые пользователи: `ivan`, `maria` (см. `src/main/resources/data.sql`).