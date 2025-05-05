INSERT INTO currency.users (name, surname, nickname, email, password)
VALUES
    ('Kirill', 'Zavyalov', 'kireally', 'kirill@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy'),
    ('Max', 'Baranovich', 'bar_ma', 'max@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy')
    ON CONFLICT DO NOTHING;

INSERT INTO currency.roles (user_id, role)
VALUES
    (1, 'ROLE_ADMIN'),
    (1, 'ROLE_USER'),
    (2, 'ROLE_USER')
    ON CONFLICT DO NOTHING;