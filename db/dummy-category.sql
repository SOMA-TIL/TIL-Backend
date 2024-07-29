insert into category (name, topic, created_date, modified_date)
values
  -- 네트워크
    ("네트워크", "HTTP", now(), now()),
    ("네트워크", "OSI 7계층", now(), now()),
    ("네트워크", "Transport 계층", now(), now()),
  -- 데이터베이스
    ("데이터베이스", "트랜잭션", now(), now()),
    ("데이터베이스", "인덱스", now(), now()),
  -- 자료구조
    ("자료구조", "해시", now(), now()),
    ("자료구조", "이진 탐색 트리", now(), now()),
    ("자료구조", "연결 리스트", now(), now()),
  -- 운영체제
    ("운영체제", "프로세스와 스레드", now(), now()),
    ("운영체제", "동시성", now(), now()),
    ("운영체제", "페이징", now(), now());
