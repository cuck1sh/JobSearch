INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type)
VALUES ('Егор', 'Кирин', 23, 'egor.kirin20@gmail.com', 'qwe', '996779088897', './data/images/default.png',
        'Соискатель'),
       ('Жмых Airlines', null, 75, 'zhmych@gmail.com', 'qwert', '996779242526', './data/images/default.png',
        'Работодатель');

insert into categories (name, parent_id)
values ('Программирование', null),
       ('WEB программист', (select id from CATEGORIES where NAME = 'Программирование')),
       ('QA Engineer', (select id from CATEGORIES where NAME = 'Программирование')),
       ('Дизайн', null),
       ('UI/UX дизайнер', (select id from CATEGORIES where NAME = 'Дизайн')),
       ('Графический дизайнер', (select id from CATEGORIES where NAME = 'Дизайн'));

insert into resumes (user_id, name, category_id, salary, is_active, created_date, update_time)
values ((select id from USERS where EMAIL = 'egor.kirin20@gmail.com'),
        'Для WEB-a',
        (select id from CATEGORIES where name = 'WEB программист'),
        999999.99,
        true,
        '2024-03-11 21:41:12',
        null),
       ((select id from USERS where EMAIL = 'egor.kirin20@gmail.com'),
        'Для программирования',
        (select id from CATEGORIES where name = 'Программирование'),
        995999.99,
        false,
        '2024-03-11 21:41:12',
        '2024-03-11 21:48:12');


insert into contact_types (type)
values ('Phone number'),
       ('Email'),
       ('Facebook'),
       ('LinkedIn'),
       ('Telegram');

insert into contact_info (type_id, resume_id, "value")
values ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Для WEB-a'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        '996779880897'),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Для WEB-a'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        'egor.kirin20@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Для программирования'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        '996779880897'),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Для программирования'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        'egor.kirin20@gmail.com');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id
         from RESUMES
         where NAME = 'Для WEB-a'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        'Attractor school',
        'Java backend',
        '2023-09-12',
        '2024-09-01',
        'Junior'),
       ((select id
         from RESUMES
         where NAME = 'Для программирования'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        'KRSU',
        'Program engineering',
        '2017-09-01',
        '2020-06-01',
        'Bachelor');


insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id
         from RESUMES
         where NAME = 'Для WEB-a'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        1,
        'Google',
        'Web developer',
        'backend'),
       ((select id
         from RESUMES
         where NAME = 'Для программирования'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        1,
        'Facebook',
        'Mobile developer',
        'apps');


insert into vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, user_id, created_date,
                       update_date)
values ('Мобильный разработчик',
        'middle на flutter',
        (select id from CATEGORIES where NAME = 'Программирование'),
        100000.00,
        2,
        4,
        true,
        (select id from USERS where EMAIL = 'zhmych@gmail.com'),
        '2024-03-11 22:53:11',
        null),
       ('Web разработчик',
        'middle на java',
        (select id from CATEGORIES where NAME = 'WEB программист'),
        150000.00,
        2,
        3,
        true,
        (select id from USERS where EMAIL = 'zhmych@gmail.com'),
        '2024-03-10 22:53:11',
        null);

insert into responded_applicants (resume_id, vacancy_id, confirmation)
values ((select id
         from RESUMES
         where NAME = 'Для WEB-a'
           and USER_ID = (select id from USERS where users.EMAIL = 'egor.kirin20@gmail.com')),
        (select id from VACANCIES where NAME = 'Web разработчик' and description = 'middle на java'),
        true);

insert into messages (responded_applicants_id, content, timestamp)
values ((select id
         from RESPONDED_APPLICANTS
         where RESUME_ID = (
             select id
             from RESUMES
             where NAME = 'Для WEB-a'
               and USER_ID = (
                 select id
                 from USERS
                 where users.EMAIL = 'egor.kirin20@gmail.com'))
           and VACANCY_ID = (
             select id
             from VACANCIES
             where NAME = 'Web разработчик'
               and description = 'middle на java')),
        'Рады сообщить о прохождении собеседования',
        '2024-03-11 23:06:13');
