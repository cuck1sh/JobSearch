INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type)
VALUES ('Василий', 'Пупкин', 33, 'vasiliy@gmail.com', '$2a$10$hiVUeDlbsrdTDRbQNCwlIebvpNqjcjl3nE.xCVSgqBuV5QOn1drjm',
        '996-882-32-54-19', 'default.png',
        'EMPLOYEE'),
       ('Мистер', 'Пропер', 41, 'mister@gmail.com', '$2a$10$hiVUeDlbsrdTDRbQNCwlIebvpNqjcjl3nE.xCVSgqBuV5QOn1drjm',
        '996-823-11-22-33', 'default.png',
        'EMPLOYEE'),
       ('Евгений', 'Гагатун', 26, 'jeka@gmail.com', '$2a$10$hiVUeDlbsrdTDRbQNCwlIebvpNqjcjl3nE.xCVSgqBuV5QOn1drjm',
        '996-123-12-23-34', 'default.png',
        'EMPLOYEE'),
       ('Генадий', 'Кац', 53, 'genadiykaz@gmail.com', '$2a$10$hiVUeDlbsrdTDRbQNCwlIebvpNqjcjl3nE.xCVSgqBuV5QOn1drjm',
        '996-323-11-11-11', 'default.png',
        'EMPLOYEE'),
       ('Федор', 'Калганов', 23, 'fyodor@gmail.com', '$2a$10$hiVUeDlbsrdTDRbQNCwlIebvpNqjcjl3nE.xCVSgqBuV5QOn1drjm',
        '996-323-61-54-48', 'default.png',
        'EMPLOYEE'),
       ('Анатолий', 'Кинза', 34, 'toha@gmail.com', '$2a$10$hiVUeDlbsrdTDRbQNCwlIebvpNqjcjl3nE.xCVSgqBuV5QOn1drjm',
        '996-324-48-03-21', 'default.png',
        'EMPLOYEE'),
       ('Google', null, 20, 'google@gmail.com', '$2a$10$uH/AT3FXpvRCQ8SdD4Dqi..4vw1NeuddeqUEEON9ZxbEkFOtC.z0S',
        '996-999-24-25-26', 'default.png',
        'EMPLOYER'),
       ('Amazon', null, 15, 'amazon@amazon.com', '$2a$10$uH/AT3FXpvRCQ8SdD4Dqi..4vw1NeuddeqUEEON9ZxbEkFOtC.z0S',
        '996-999-24-25-25', 'default.png',
        'EMPLOYER'),
       ('Baltika', null, 50, 'baltika@mail.ru', '$2a$10$uH/AT3FXpvRCQ8SdD4Dqi..4vw1NeuddeqUEEON9ZxbEkFOtC.z0S',
        '996-999-24-25-28', 'default.png',
        'EMPLOYER'),
       ('Apple', null, 50, 'apple@apple.com', '$2a$10$uH/AT3FXpvRCQ8SdD4Dqi..4vw1NeuddeqUEEON9ZxbEkFOtC.z0S',
        '996-999-24-25-27', 'default.png',
        'EMPLOYER'),
       ('Microsoft', null, 50, 'microsoft@inbox.com', '$2a$10$uH/AT3FXpvRCQ8SdD4Dqi..4vw1NeuddeqUEEON9ZxbEkFOtC.z0S',
        '996-156-24-24-27', 'default.png',
        'EMPLOYER');

insert into categories (name, parent_id)
values ('Marketing', null),
       ('Digital marketing', (select id from CATEGORIES where NAME = 'Marketing')),
       ('Реклама и связи с общественностью', (select id from CATEGORIES where NAME = 'Marketing')),
       ('Финансы', null),
       ('Финансовый аналитик', (select id from CATEGORIES where NAME = 'Финансы')),
       ('Аудитор', (select id from CATEGORIES where NAME = 'Финансы')),
       ('Human resources', null);

insert into resumes (user_id, name, category_id, salary, is_active, created_date, update_time)
values ((select id from USERS where EMAIL = 'vasiliy@gmail.com'),
        'Невероятно опытный Графический дизайнер',
        (select id from CATEGORIES where name = 'Графический дизайнер'),
        40000,
        true,
        '2024-04-08 23:00:00',
        '2024-04-08 23:00:00'),
       ((select id from USERS where EMAIL = 'vasiliy@gmail.com'),
        'middle UI/UX дизайнер',
        (select id from CATEGORIES where name = 'UI/UX дизайнер'),
        45000,
        true,
        '2024-04-08 23:01:00',
        '2024-04-08 23:01:00'),
       ((select id from USERS where EMAIL = 'mister@gmail.com'),
        'Маркетолог опыт 3года',
        (select id from CATEGORIES where name = 'Marketing'),
        51000,
        true,
        '2024-04-08 23:08:00',
        '2024-04-08 23:08:00'),
       ((select id from USERS where EMAIL = 'mister@gmail.com'),
        'middle Digital marketing specialist',
        (select id from CATEGORIES where name = 'Digital marketing'),
        54000,
        true,
        '2024-04-08 23:09:00',
        '2024-04-08 23:09:00'),
       ((select id from USERS where EMAIL = 'jeka@gmail.com'),
        'Молодой горячий HR-специалист',
        (select id from CATEGORIES where name = 'Human resources'),
        41000,
        true,
        '2024-04-08 23:10:00',
        '2024-04-08 23:10:00'),
       ((select id from USERS where EMAIL = 'jeka@gmail.com'),
        'Специалист по рекламе и связям с общественностью',
        (select id from CATEGORIES where name = 'Реклама и связи с общественностью'),
        33000,
        true,
        '2024-04-08 23:10:00',
        '2024-04-08 23:10:00'),
       ((select id from USERS where EMAIL = 'genadiykaz@gmail.com'),
        'Специалст по анализу и аудиту финансов',
        (select id from CATEGORIES where name = 'Финансы'),
        141000,
        true,
        '2024-04-08 23:14:00',
        '2024-04-08 23:14:00'),
       ((select id from USERS where EMAIL = 'genadiykaz@gmail.com'),
        'Специалст по финансовой аналитике',
        (select id from CATEGORIES where name = 'Финансовый аналитик'),
        145000,
        true,
        '2024-04-08 23:15:00',
        '2024-04-08 23:15:00'),
       ((select id from USERS where EMAIL = 'genadiykaz@gmail.com'),
        'Аудитор финансовой деятельности, опыт 21год',
        (select id from CATEGORIES where name = 'Аудитор'),
        150000,
        true,
        '2024-04-08 23:16:00',
        '2024-04-08 23:16:00'),
       ((select id from USERS where EMAIL = 'fyodor@gmail.com'),
        'Junior Web-программист',
        (select id from CATEGORIES where name = 'WEB программист'),
        35000,
        true,
        '2024-04-08 23:18:00',
        '2024-04-08 23:18:00'),
       ((select id from USERS where EMAIL = 'fyodor@gmail.com'),
        'Junior тестировщик-ПО',
        (select id from CATEGORIES where name = 'QA Engineer'),
        30000,
        true,
        '2024-04-08 23:19:00',
        '2024-04-08 23:19:00'),
       ((select id from USERS where EMAIL = 'toha@gmail.com'),
        'Специалист по маркетингу',
        (select id from CATEGORIES where name = 'Marketing'),
        37000,
        true,
        '2024-04-08 23:23:00',
        '2024-04-08 23:23:00'),
       ((select id from USERS where EMAIL = 'toha@gmail.com'),
        'Специалист по Дизайну и маркетингу',
        (select id from CATEGORIES where name = 'Дизайн'),
        40000,
        true,
        '2024-04-08 23:24:00',
        '2024-04-08 23:24:00');

insert into contacts_info (type_id, resume_id, info)
values ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Специалист по Дизайну и маркетингу'
           and USER_ID = (select id from USERS where users.EMAIL = 'toha@gmail.com')),
        '996-324-48-03-21'),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Специалист по Дизайну и маркетингу'
           and USER_ID = (select id from USERS where users.EMAIL = 'toha@gmail.com')),
        (select EMAIL from USERS where users.EMAIL = 'toha@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Специалист по маркетингу'
           and USER_ID = (select id from USERS where users.EMAIL = 'toha@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'toha@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Специалист по маркетингу'
           and USER_ID = (select id from USERS where users.EMAIL = 'toha@gmail.com')),
        'egor.kirin20@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Junior тестировщик-ПО'
           and USER_ID = (select id from USERS where users.EMAIL = 'fyodor@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'fyodor@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Junior тестировщик-ПО'
           and USER_ID = (select id from USERS where users.EMAIL = 'fyodor@gmail.com')),
        'fyodor@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Junior Web-программист'
           and USER_ID = (select id from USERS where users.EMAIL = 'fyodor@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'fyodor@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Junior Web-программист'
           and USER_ID = (select id from USERS where users.EMAIL = 'fyodor@gmail.com')),
        'fyodor@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Специалст по анализу и аудиту финансов'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Специалст по анализу и аудиту финансов'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        'genadiykaz@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Специалст по финансовой аналитике'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Специалст по финансовой аналитике'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        'genadiykaz@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Аудитор финансовой деятельности, опыт 21год'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Аудитор финансовой деятельности, опыт 21год'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        'genadiykaz@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Специалист по рекламе и связям с общественностью'
           and USER_ID = (select id from USERS where users.EMAIL = 'jeka@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'jeka@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Специалист по рекламе и связям с общественностью'
           and USER_ID = (select id from USERS where users.EMAIL = 'jeka@gmail.com')),
        'jeka@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Молодой горячий HR-специалист'
           and USER_ID = (select id from USERS where users.EMAIL = 'jeka@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'jeka@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Молодой горячий HR-специалист'
           and USER_ID = (select id from USERS where users.EMAIL = 'jeka@gmail.com')),
        'jeka@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'middle Digital marketing specialist'
           and USER_ID = (select id from USERS where users.EMAIL = 'mister@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'mister@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'middle Digital marketing specialist'
           and USER_ID = (select id from USERS where users.EMAIL = 'mister@gmail.com')),
        'mister@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Маркетолог опыт 3года'
           and USER_ID = (select id from USERS where users.EMAIL = 'mister@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'mister@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Маркетолог опыт 3года'
           and USER_ID = (select id from USERS where users.EMAIL = 'mister@gmail.com')),
        'mister@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'middle UI/UX дизайнер'
           and USER_ID = (select id from USERS where users.EMAIL = 'vasiliy@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'vasiliy@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'middle UI/UX дизайнер'
           and USER_ID = (select id from USERS where users.EMAIL = 'vasiliy@gmail.com')),
        'vasiliy@gmail.com'),
       ((select id from CONTACT_TYPES where type = 'Phone number'),
        (select id
         from RESUMES
         where NAME = 'Невероятно опытный Графический дизайнер'
           and USER_ID = (select id from USERS where users.EMAIL = 'vasiliy@gmail.com')),
        (select PHONE_NUMBER from USERS where users.EMAIL = 'vasiliy@gmail.com')),
       ((select id from CONTACT_TYPES where type = 'Email'),
        (select id
         from RESUMES
         where NAME = 'Невероятно опытный Графический дизайнер'
           and USER_ID = (select id from USERS where users.EMAIL = 'vasiliy@gmail.com')),
        'vasiliy@gmail.com');

insert into education_info (resume_id, institution, program, start_date, end_date, degree)
values ((select id
         from RESUMES
         where NAME = 'Невероятно опытный Графический дизайнер'
           and USER_ID = (select id from USERS where users.EMAIL = 'vasiliy@gmail.com')),
        'Kek school',
        'Graphical design',
        '2023-09-12',
        '2024-09-01',
        'Junior'),
       ((select id
         from RESUMES
         where NAME = 'middle UI/UX дизайнер'
           and USER_ID = (select id from USERS where users.EMAIL = 'vasiliy@gmail.com')),
        'Kek school',
        'Graphical design',
        '2023-09-12',
        '2024-09-01',
        'Junior'),
       ((select id
         from RESUMES
         where NAME = 'Маркетолог опыт 3года'
           and USER_ID = (select id from USERS where users.EMAIL = 'mister@gmail.com')),
        'KTHF',
        'Marketing',
        '2020-09-01',
        '2024-06-20',
        'Bachelor'),
       ((select id
         from RESUMES
         where NAME = 'middle Digital marketing specialist'
           and USER_ID = (select id from USERS where users.EMAIL = 'mister@gmail.com')),
        'KTHF',
        'Marketing',
        '2020-09-01',
        '2024-06-20',
        'Bachelor'),
       ((select id
         from RESUMES
         where NAME = 'Молодой горячий HR-специалист'
           and USER_ID = (select id from USERS where users.EMAIL = 'jeka@gmail.com')),
        'KRSU',
        'Advertisement',
        '2020-09-01',
        '2024-06-20',
        'Bachelor'),
       ((select id
         from RESUMES
         where NAME = 'Специалист по рекламе и связям с общественностью'
           and USER_ID = (select id from USERS where users.EMAIL = 'jeka@gmail.com')),
        'KRSU',
        'Advertisement',
        '2020-09-01',
        '2024-06-20',
        'Bachelor'),
       ((select id
         from RESUMES
         where NAME = 'Аудитор финансовой деятельности, опыт 21год'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        'Harvard',
        'Finance and credit',
        '2020-09-01',
        '2024-06-20',
        'Bachelor'),
       ((select id
         from RESUMES
         where NAME = 'Специалст по финансовой аналитике'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        'Harvard',
        'Finance and credit',
        '2020-09-01',
        '2026-06-20',
        'master'),
       ((select id
         from RESUMES
         where NAME = 'Специалст по анализу и аудиту финансов'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        'Harvard',
        'Finance and credit',
        '2020-09-01',
        '2026-06-20',
        'master'),
       ((select id
         from RESUMES
         where NAME = 'Junior Web-программист'
           and USER_ID = (select id from USERS where users.EMAIL = 'fyodor@gmail.com')),
        'KGTU',
        'Program engineering',
        '2020-09-01',
        '2024-06-20',
        'Bachelor'),
       ((select id
         from RESUMES
         where NAME = 'Junior тестировщик-ПО'
           and USER_ID = (select id from USERS where users.EMAIL = 'fyodor@gmail.com')),
        'Harvard',
        'Program engineering',
        '2020-09-01',
        '2024-06-20',
        'Bachelor'),
       ((select id
         from RESUMES
         where NAME = 'Специалист по Дизайну и маркетингу'
           and USER_ID = (select id from USERS where users.EMAIL = 'toha@gmail.com')),
        'KRSU',
        'Marketing',
        '2020-09-01',
        '2024-06-20',
        'Bachelor'),
       ((select id
         from RESUMES
         where NAME = 'Специалист по маркетингу'
           and USER_ID = (select id from USERS where users.EMAIL = 'toha@gmail.com')),
        'KRSU',
        'Marketing',
        '2020-09-01',
        '2024-06-20',
        'Bachelor');

insert into work_experience_info (resume_id, years, company_name, position, responsibilities)
values ((select id
         from RESUMES
         where NAME = 'Специалст по анализу и аудиту финансов'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        5,
        'Google',
        'Auditor',
        'audit'),
       ((select id
         from RESUMES
         where NAME = 'Специалст по финансовой аналитике'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        10,
        'qwe&ewq',
        'Analyst',
        'financial analytics'),
       ((select id
         from RESUMES
         where NAME = 'Аудитор финансовой деятельности, опыт 21год'
           and USER_ID = (select id from USERS where users.EMAIL = 'genadiykaz@gmail.com')),
        6,
        'Facebook',
        'Finance specialist',
        'finance flows');

insert into vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, user_id, created_date,
                       update_time)
values ('Тестирование программного обеспечения',
        'middle, от 2ух лет, знание Python',
        (select id from CATEGORIES where NAME = 'QA Engineer'),
        230500,
        2,
        null,
        true,
        (select id from USERS where EMAIL = 'google@gmail.com'),
        '2024-04-09 00:31:11',
        '2024-04-09 00:31:11'),
       ('Web разработка программного обеспечения',
        'Senior, опыт работы в коммерческой деятельности не менее 5 лет, знание javascript',
        (select id from CATEGORIES where NAME = 'WEB программист'),
        470000,
        5,
        null,
        true,
        (select id from USERS where EMAIL = 'google@gmail.com'),
        '2024-04-09 00:32:11',
        '2024-04-09 00:32:11'),
       ('Web разработка программного обеспечения',
        'Senior, опыт работы в коммерческой деятельности не менее 5 лет, знание kotlin',
        (select id from CATEGORIES where NAME = 'WEB программист'),
        470000,
        5,
        null,
        true,
        (select id from USERS where EMAIL = 'google@gmail.com'),
        '2024-04-09 00:32:11',
        '2024-04-09 00:32:11'),
       ('Web разработка, backend',
        'middle разработчик, опыт работы не менее 3 лет, знание java',
        (select id from CATEGORIES where NAME = 'WEB программист'),
        410000,
        3,
        null,
        true,
        (select id from USERS where EMAIL = 'amazon@amazon.com'),
        '2024-04-09 00:35:11',
        '2024-04-09 00:35:11'),
       ('Digital marketing specialist',
        'Специалист в области Digital маркетинга',
        (select id from CATEGORIES where NAME = 'Digital marketing'),
        370000,
        4,
        null,
        true,
        (select id from USERS where EMAIL = 'amazon@amazon.com'),
        '2024-04-09 00:37:11',
        '2024-04-09 00:37:11'),
       ('UI/UX designer',
        'middle UI/UX специалист, умение работать в photozhop',
        (select id from CATEGORIES where NAME = 'UI/UX дизайнер'),
        120000,
        2,
        null,
        true,
        (select id from USERS where EMAIL = 'amazon@amazon.com'),
        '2024-04-09 00:38:11',
        '2024-04-09 00:38:11'),
       ('Реклама и связи с общественностью',
        'Специалист в области рекламы и связей с общественностью опыт от 3ех лет',
        (select id from CATEGORIES where NAME = 'Реклама и связи с общественностью'),
        90000,
        3,
        null,
        true,
        (select id from USERS where EMAIL = 'baltika@mail.ru'),
        '2024-04-09 00:41:11',
        '2024-04-09 00:41:11'),
       ('HR-специалист',
        'Требуется сотрудник в отдел кадров, опыт от 4лет',
        (select id from CATEGORIES where NAME = 'Human resources'),
        60000,
        4,
        null,
        true,
        (select id from USERS where EMAIL = 'baltika@mail.ru'),
        '2024-04-09 00:43:11',
        '2024-04-09 00:43:11'),
       ('Swift разработчик',
        'Разработка внутреннего программного обеспечения',
        (select id from CATEGORIES where NAME = 'Программирование'),
        460000,
        6,
        null,
        true,
        (select id from USERS where EMAIL = 'apple@apple.com'),
        '2024-04-09 00:45:11',
        '2024-04-09 00:45:11'),
       ('Дизайнер',
        'Разработка и поддержание корпоративной визульной айдентики',
        (select id from CATEGORIES where NAME = 'Дизайн'),
        500000,
        4,
        null,
        true,
        (select id from USERS where EMAIL = 'apple@apple.com'),
        '2024-04-09 00:47:11',
        '2024-04-09 00:47:11'),
       ('C++ разработчик',
        'Разработка программного обеспечения для заводских контроллеров',
        (select id from CATEGORIES where NAME = 'Программирование'),
        400000,
        6,
        null,
        true,
        (select id from USERS where EMAIL = 'microsoft@inbox.com'),
        '2024-04-09 00:49:11',
        '2024-04-09 00:49:11'),
       ('C# разработчик',
        'Разработка программного обеспечения для онлайн платформ',
        (select id from CATEGORIES where NAME = 'WEB программист'),
        350000,
        4,
        null,
        true,
        (select id from USERS where EMAIL = 'microsoft@inbox.com'),
        '2024-04-09 00:49:11',
        '2024-04-09 00:49:11'),
       ('Python разработчик',
        'Тестирование онлайн платформ и микросервисов',
        (select id from CATEGORIES where NAME = 'QA Engineer'),
        350000,
        4,
        null,
        true,
        (select id from USERS where EMAIL = 'microsoft@inbox.com'),
        '2024-04-09 00:49:11',
        '2024-04-09 00:49:11');