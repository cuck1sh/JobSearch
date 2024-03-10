CREATE TABLE IF not EXISTS users
(
    id
    INT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    name
    VARCHAR
(
    50
),
    surname VARCHAR
(
    50
),
    age integer,
    email varchar
(
    50
),
    password varchar
(
    50
),
    phone_number varchar
(
    55
),
    avatar VARCHAR
(
    100
),
    account_type VARCHAR
(
    50
)
    );

INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type)
VALUES ('Егор', 'Кирин', 23, 'egor.kirin20@gmail.com', 'qwe', '996779088897', './data/images/default.png',
        'Соискатель'),
       ('Валерий', 'Жмышенко', 75, 'zhmych@gmail.com', 'qwert', '996779242526', './data/images/default.png',
        'Работодатель');

