INSERT INTO currency.users (name, surname, nickname, email, password)
VALUES
    ('Kirill', 'Zavyalov', 'kireally', 'kirill@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy'),
    ('Max', 'Baranovich', 'bar_ma', 'max@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy'),
    ('Sanya', 'Bulkin', 'bulych', 'bulych@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy'),
    ('Ilya', 'Exile', 'exile', 'exile@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy'),
    ('Slava', 'Leontyev', 'buster', 'buster@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy'),
    ('Mihail', 'Litvin', 'Litvin', 'litvin@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy'),
    ('Alexander', 'Paradeev', 'paradeev', 'paradeev@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy'),
    ('Lyosha', 'Derevyashkin', 'koreshzy', 'koreshzy@gmail.com', '$2y$10$VmCTaZaDlo/R/uDBrTGnLO5L9JO/kjUkWom4YuTXA71S3aaITSvOy')
ON CONFLICT DO NOTHING;

