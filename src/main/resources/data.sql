CREATE TABLE IF not EXISTS users
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        text        not null,
    surname     text,
    age         integer,
    email       text unique not null,
    password    text        not null,
    phone_number varchar(55) not null,
    avatar      text DEFAULT './images/default.png',
    account_type VARCHAR(50) not null
);

INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type)
VALUES ('Егор', 'Кирин', 23, 'egor.kirin20@gmail.com', 'qwe', '996779088897', './data/images/default.png',
        'Соискатель'),
       ('Валерий', 'Жмышенко', 75, 'zhmych@gmail.com', 'qwert', '996779242526', './data/images/default.png',
        'Работодатель');

CREATE TABLE IF not EXISTS categories
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      text not null,
    parent_id INT,
    foreign key (parent_id) references categories (id)
        on delete restrict
        on update cascade
);

insert into categories (name, parent_id)
values ('Программирование', null),
       ('WEB программист', 1),
       ('QA Engineer', 1),
       ('Дизайн', null),
       ('UI/UX дизайнер', 4),
       ('Графический дизайнер', 4);

CREATE TABLE IF not EXISTS resumes
(
    id           int auto_increment primary key,
    user_id      int,
    name         text not null,
    category_id  int,
    salary       real,
    is_active    boolean,
    created_date timestamp,
    update_time  timestamp,
    foreign key (user_id) references users (id)
        on delete restrict
        on update cascade,
    foreign key (category_id) references categories (id)
        on delete restrict
        on update cascade
);

insert into resumes (user_id, name, category_id, salary, is_active, created_date, update_time)
values (1, 'Egor Kirin', 2, 999999.99, true, '2024-03-11 21:41:12', null),
       (1, 'Egor Kirin', 1, 995999.99, false, '2024-03-11 21:41:12', '2024-03-11 21:48:12');


CREATE TABLE IF not EXISTS contact_types
(
    id   int auto_increment primary key,
    type text
);

insert into contact_types (type)
values ('Phone number'),
       ('Email'),
       ('Facebook'),
       ('Instagram');

CREATE TABLE IF not EXISTS contact_info
(
    id        int auto_increment primary key,
    type_id   int,
    resume_id int,
    "value"   TEXT NOT NULL,
    foreign key (type_id) references contact_types (id)
        on delete restrict
        on update cascade,
    foreign key (resume_id) references resumes (id)
        on delete restrict
        on update cascade
);

insert into contact_info (type_id, resume_id, "value")
values (1, 1, '996779880897'),
       (2, 1, 'egor.kirin20@gmail.com');

CREATE TABLE IF not EXISTS education_info
(
    id          int auto_increment primary key,
    resume_id   int  not null,
    institution text not null,
    program     text not null,
    start_date  date not null,
    end_date    date not null,
    degree      text,
    foreign key (resume_id) references resumes (id)
        on delete restrict
        on update cascade
);

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values (1, 'Attractor school', 'Java backend', '2023-09-12', '2024-09-01', null),
       (2, 'KRSU', 'Program engineering', '2017-09-01', '2020-06-01', 'Bachelor');

CREATE TABLE IF not EXISTS work_experience_info
(
    id               int auto_increment primary key,
    resume_id        int  not null,
    years            int  not null,
    company_name     text not null,
    position         text not null,
    responsibilities text not null,
    foreign key (resume_id) references resumes (id)
        on delete restrict
        on update cascade
);

insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values (1, 1, 'Google', 'Web developer', 'backend'),
       (2, 1, 'Facebook', 'Mobile developer', 'apps');

CREATE TABLE IF not EXISTS vacancies
(
    id           int auto_increment primary key,
    name         text not null,
    description  text,
    category_id  int,
    salary       real,
    exp_from     int,
    exp_to       int,
    is_active    boolean,
    user_id      int,
    created_date timestamp,
    update_date  timestamp,
    foreign key (category_id) references categories (id)
        on delete restrict
        on update cascade,
    foreign key (user_id) references users (id)
        on delete restrict
        on update cascade
);

insert into vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, user_id, created_date,
                       update_date)
values ('Жмышенко Валерий Альбертович', 'Ищем в контору программиста', 1, null, 2, 4, true, 2, '2024-03-11 22:53:11',
        null),
       ('Жмышенко Валерий Альбертович', 'Ищем в контору Web-разработчика', 2, null, 2, 3, true, 2,
        '2024-03-10 22:53:11', null);

CREATE TABLE IF not EXISTS responded_applicants
(
    id           int auto_increment primary key,
    resume_id    int,
    vacancy_id   int,
    confirmation boolean,
    foreign key (resume_id) references resumes (id)
        on delete restrict
        on update cascade,
    foreign key (vacancy_id) references vacancies (id)
        on delete restrict
        on update cascade
);

insert into responded_applicants (resume_id, vacancy_id, confirmation)
values (1, 2, true);

CREATE TABLE IF not EXISTS messages
(
    id                      int auto_increment primary key,
    responded_applicants_id int,
    content                 text,
    timestamp               timestamp,
    foreign key (responded_applicants_id) references responded_applicants (id)
        on delete restrict
        on update cascade
);

insert into messages (responded_applicants_id, content, timestamp)
values (1, 'Контент письма', '2024-03-11 23:06:13');
