drop table if exists students;

create table students (
    id SERIAL,
    surname varchar(255) not null,
    first_name varchar(255) not null,
    patronymic varchar(255) not null,
    gender varchar(1) not null,
    birthday date not null,
    form_of_education varchar(255) not null,
    number_of_failed_exams integer not null,
    group_id integer not null
)

insert into students (surname, first_name, patronymic, gender, birthday, form_of_education, number_of_failed_exams, group_id)
VALUES
    ('olegov', 'oleg', 'olegov','m','2020-12-12', 'budget',0,2),
    ('myasnikov', 'oleg', 'olegov','m','2020-12-12', 'budget',0,2),
    ('olegov', 'oleg', 'olegov','m','2020-12-12', 'budget',0,2),
    ('olegov', 'oleg', 'olegov','m','2020-12-12', 'budget',0,2),
    ('olegov', 'oleg', 'olegov','m','2020-12-12', 'budget',0,2),
    ('olegov', 'oleg', 'olegov','m','2020-12-12', 'budget',0,2),
    ('olegov', 'oleg', 'olegov','m','2020-12-12', 'budget',0,2),
    ('olegov', 'oleg', 'olegov','m','2020-12-12', 'budget',0,2)
