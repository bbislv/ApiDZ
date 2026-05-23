-- Тестовые пользователи для локальной проверки (H2).
-- ivan
INSERT INTO users (user_id, username, first_name, last_name)
VALUES ('1', 'ivan', 'Иван', 'Петров');

INSERT INTO user_transactions (user_id, product_type, transaction_type, amount)
VALUES ('1', 'DEBIT', 'DEPOSIT', 50000);

INSERT INTO user_transactions (user_id, product_type, transaction_type, amount)
VALUES ('1', 'CREDIT', 'DEPOSIT', 10000);

-- maria
INSERT INTO users (user_id, username, first_name, last_name)
VALUES ('2', 'maria', 'Мария', 'Сидорова');

INSERT INTO user_transactions (user_id, product_type, transaction_type, amount)
VALUES ('2', 'DEBIT', 'DEPOSIT', 150000);

INSERT INTO user_transactions (user_id, product_type, transaction_type, amount)
VALUES ('2', 'DEBIT', 'WITHDRAW', 20000);
