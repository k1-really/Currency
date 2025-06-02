INSERT INTO currency.roles (user_id, role)
VALUES
    (1, 'ROLE_ADMIN'),
    (1, 'ROLE_USER'),
    (2, 'ROLE_USER')
    ON CONFLICT DO NOTHING;