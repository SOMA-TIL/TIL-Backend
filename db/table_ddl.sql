DROP TABLE IF EXISTS user, category, favorite_problem, problem, problem_category, solve_problem, interview;

CREATE TABLE user
(
    id            bigint auto_increment primary key,
    email         varchar(255) not null,
    nickname      varchar(255) not null,
    password      varchar(255) not null,
    platform      enum ('TIL', 'GOOGLE', 'KAKAO', 'NAVER')       not null,
    role          enum ('ADMIN', 'USER')                         not null,
    status        enum ('ACTIVE', 'DELETED', 'INACTIVE', 'STOP') not null,
    created_date  datetime(6)                                    not null,
    modified_date datetime(6)                                    not null
);


CREATE TABLE category
(
    id            bigint auto_increment primary key,
    tag           varchar(255) not null,
    created_date  datetime(6)  not null,
    modified_date datetime(6)  not null
);

CREATE TABLE favorite_problem
(
    id            bigint auto_increment primary key,
    user_id       bigint not null,
    problem_id    bigint not null,
    created_date  datetime(6) not null,
    modified_date datetime(6) not null
);

CREATE TABLE problem
(
    id            bigint auto_increment primary key,
    title         varchar(255) not null,
    question      text         not null,
    solution      text         not null,
    grading       text null,
    level         int          not null,
    created_date  datetime(6)  not null,
    modified_date datetime(6)  not null
);

CREATE TABLE problem_category
(
    category_id   bigint not null,
    id            bigint not null primary key,
    problem_id    bigint not null,
    created_date  datetime(6) not null,
    modified_date datetime(6) not null
);

CREATE TABLE solve_problem
(
    id            bigint auto_increment primary key,
    problem_id    bigint not null,
    user_id       bigint not null,
    answer        text null,
    status        enum ('PASS', 'FAIL', 'PENDING') not null,
    created_date  datetime(6)                      not null,
    modified_date datetime(6)                      not null
);

CREATE TABLE interview
(
    id            bigint auto_increment primary key,
    code          varchar(20)                      not null,
    status        enum ('PROCESSING', 'DONE', 'ABORTED') not null,
    user_id       bigint                           not null,
    created_date  datetime(6)                      not null,
    modified_date datetime(6)                      not null
);
