DROP TABLE IF EXISTS user, category, favorite_problem, problem, problem_category, solve_problem,
  interview, grading, interview_category, interview_problem, speech_interview_problem,
  til_history, problem_hourly_view_statistics;
DROP VIEW IF EXISTS problem_statistics;

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
    id            bigint auto_increment primary key,
    problem_id    bigint not null,
    category_id   bigint not null,
    created_date  datetime(6) not null,
    modified_date datetime(6) not null
);

CREATE TABLE solve_problem
(
    id            bigint auto_increment primary key,
    problem_id    bigint not null,
    user_id       bigint not null,
    answer        text null,
    status        enum ('PENDING', 'COMPLETED', 'ERROR') not null,
    created_date  datetime(6)                      not null,
    modified_date datetime(6)                      not null
);

CREATE TABLE interview
(
    id            bigint auto_increment primary key,
    code          varchar(20)                     not null unique,
    status        enum ('CREATING', 'PROCESSING', 'PENDING', 'DONE', 'ERROR', 'ABORTED') not null,
    type          enum ('NORMAL', 'PORTFOLIO')     not null,
    question_size int not null,
    portfolio     text null,
    user_id       bigint                           not null,
    created_date  datetime(6)                      not null,
    modified_date datetime(6)                      not null
);

CREATE TABLE grading
(
    id            bigint auto_increment primary key,
    type          enum ('INTERVIEW', 'PROBLEM') not null,
    target_id     bigint not null,
    result        enum ('PASS', 'FAIL') not null,
    comment       text null,
    created_date  datetime(6) not null,
    modified_date datetime(6) not null
);

CREATE TABLE interview_category
(
    id            bigint auto_increment not null primary key,
    interview_id  bigint not null,
    category_id   bigint not null,
    created_date  datetime(6) not null,
    modified_date datetime(6) not null
);

CREATE TABLE interview_problem
(
    id             bigint auto_increment not null primary key,
    answer         text null,
    sequence       int    not null,
    status         enum('UNSOLVED', 'SOLVED') not null,
    grading_status enum('IDLE', 'PENDING', 'COMPLETED', 'ERROR') not null,
    interview_id   bigint not null,
    problem_id     bigint not null,
    created_date   datetime(6) not null,
    modified_date  datetime(6) not null
);

CREATE TABLE speech_interview_problem
(
    id             bigint auto_increment not null primary key,
    question       text   not null,
    answer         text null,
    sequence       int    not null,
    status         enum('UNSOLVED', 'SOLVED') not null,
    grading_status enum('IDLE', 'PENDING', 'COMPLETED', 'ERROR') not null,
    interview_id   bigint not null,
    created_date   datetime(6) not null,
    modified_date  datetime(6) not null
);

CREATE TABLE til_history
(
    id           bigint auto_increment not null primary key,
    action       varchar(30) not null,
    user_id      bigint      null,
    target       varchar(30) not null,
    target_id    bigint      null,
    details       text        null,
    created_date datetime(6) not null
);

CREATE TABLE problem_hourly_view_statistics (
    id          bigint auto_increment not null primary key,
    statistic_date  date not null,
    statistic_hour  tinyint not null check (statistic_hour BETWEEN 0 AND 23),
    problem_id    bigint not null,
    view_count    bigint null,
    created_date  datetime(6) not null default current_timestamp(6)
);

-- TEMPORARY VIEW FOR PROBLEM STATISTICS
CREATE
OR REPLACE VIEW problem_statistics AS
SELECT sp.problem_id                                                 AS problem_id,
       SUM(CASE WHEN g.result = 'PASS' THEN 1 ELSE 0 END)            AS passed_count,
       SUM(CASE WHEN g.result = 'FAIL' THEN 1 ELSE 0 END)            AS failed_count,
       SUM(CASE WHEN g.result IN ('PASS', 'FAIL') THEN 1 ELSE 0 END) AS attempted_count,
       CASE
           WHEN SUM(CASE WHEN g.result IN ('PASS', 'FAIL') THEN 1 ELSE 0 END) = 0 THEN 0
           ELSE ROUND(SUM(CASE WHEN g.result = 'PASS' THEN 1 ELSE 0 END) * 100.0 / SUM(CASE WHEN g.result IN ('PASS', 'FAIL') THEN 1 ELSE 0 END), 1)
           END                                                       AS pass_rate
FROM solve_problem sp
         JOIN grading g ON sp.id = g.target_id AND g.`type` = 'PROBLEM'
WHERE sp.status = 'COMPLETED'
GROUP BY sp.problem_id
ORDER BY problem_id;
