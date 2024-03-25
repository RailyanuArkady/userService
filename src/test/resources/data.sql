INSERT INTO users
    (birthdate, created_at, external_id, is_deleted, modified_at, phone, photo_id, sex, id)
VALUES
    ('2024-03-11', CURRENT_TIMESTAMP, '3fa85f64-5717-4562-b3fc-2c963f66afa6', false, CURRENT_TIMESTAMP, '8 (921) 331-11-64', '3fa85f64-5717-4562-b3fc-2c963f66afa6', 'MALE', 2);

INSERT INTO passports
    (created_at, external_id, modified_at, passport_date_of_issue, passport_division_code, passport_division_name, passport_number, passport_series, user_id, id)
VALUES
    (CURRENT_TIMESTAMP, '3fa85f64-5717-4562-b3fc-2c963f66afa6', CURRENT_TIMESTAMP, '2024-03-11', '125634', 'string', '123564', '3124', 2, 2);

INSERT INTO users
    (birthdate, created_at, external_id, is_deleted, modified_at, phone, photo_id, sex, id)
VALUES
    ('2024-03-11', CURRENT_TIMESTAMP, '3fa85f64-7777-4562-b3fc-2c963f66afa6', false, CURRENT_TIMESTAMP, '8 (921) 313-16-64', '3fa85f64-5717-4562-b3fc-2c963f66afa6', 'MALE', 3);

INSERT INTO passports
    (created_at, external_id, modified_at, passport_date_of_issue, passport_division_code, passport_division_name, passport_number, passport_series, user_id, id)
VALUES
    (CURRENT_TIMESTAMP, '3fa85f64-1111-4562-b3fc-2c963f66afa6', CURRENT_TIMESTAMP, '2024-03-11', '333333', 'strong', '555555', '4444', 3, 3);