insert into user (id, username, password, active, registration_date, email)
values (1, 'admin', '$2a$08$pKe3uV5kSb358BIotDLRhOOVSqNKvNVbiqpS/CSYyJjVc0MvF8xyG', 1, SYSDATE() , '45678913579@mail.ru');

insert into user_role (user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN');

insert into user (id, username, password, active, registration_date, email)
values (2, 'teacher', '$2a$08$pKe3uV5kSb358BIotDLRhOOVSqNKvNVbiqpS/CSYyJjVc0MvF8xyG', 1, SYSDATE() , '45678913579@mail.ru');

insert into user_role (user_id, roles)
values (2, 'USER'),
       (2, 'TEACHER');

insert into user (id, username, password, active, registration_date, email)
values (3, 'user', '$2a$08$pKe3uV5kSb358BIotDLRhOOVSqNKvNVbiqpS/CSYyJjVc0MvF8xyG', 1, SYSDATE() , '45678913579@mail.ru');

insert into user_role (user_id, roles)
values (3, 'USER');