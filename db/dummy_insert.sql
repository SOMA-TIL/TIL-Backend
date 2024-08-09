-- =================== user ===================
INSERT INTO user (email, nickname, password, platform, role, status, created_date, modified_date)
VALUES
    -- admin (password1234)
    ("admin@gmail.com", "admin", "$2a$10$Yq0ykHrhZpyTaU8qRlQ2gO/tt2wGH/.2PDhTWz2rbGhp6JBq3zj9G", "TIL", "ADMIN", "ACTIVE", now(), now()),
    -- status tester (password1234)
    ("active@gmail.com", "활성테스터", "$2a$10$HLIp6eyR1TBj8p4ym8R4lujjoK9/YmYQ.S7vQnE.tvTUv66c3KEI2", "TIL", "USER", "ACTIVE", now(), now()),
    ("inactive@gmail.com", "비활성테스터", "$2a$10$.PP19y7.Sh219cTjIjf0IONl86qw.jjzgqrN5ys/Rq9YQ/jAoF8oS", "TIL", "USER", "INACTIVE", now(), now()),
    ("stop@gmail.com", "멈춤테스터", "$2a$10$O3su9ESC2LihHEYb9ju7Pe5fw28Ymk9f6UexKTrFKNaxbyFyGr7k2", "TIL", "USER", "STOP", now(), now()),
    ("deleted@gmail.com", "삭제됨테스터", "$2a$10$Sh87ZPuSpa1esDabT5TTOOaSaq77bgRhDQOxcbhNiX35xPfLLpRNW", "TIL", "USER", "DELETED", now(), now()),
    -- platform tester (password1234)
    ("til@gmail.com", "TIL테스터", "$2a$10$KVjNfNIjrXmpy7kOBVTgLearEEL9X0RFokw0rMWuObSq4fCEhhO9i", "TIL", "USER", "ACTIVE", now(), now()),
    ("google@gmail.com", "구글테스터", "password1234", "GOOGLE", "USER", "ACTIVE", now(), now()),
    ("naver@naver.com", "네이버테스터", "password1234", "NAVER", "USER", "ACTIVE", now(), now()),
    ("kakao@gmail.com", "카카오테스터", "password1234", "KAKAO", "USER", "ACTIVE", now(), now()),
    -- dummy users (password1, password2, ..., password12)
    ("user1@gmail.com", "유저1", "$2a$10$i6v4UBbjOMYckcEtKU.sHuJKQzembjPezQ4uVeB5AuUDabO8/aP3C", "TIL", "USER", "ACTIVE", now(), now()),
    ("user2@gmail.com", "유저2", "password1234", "GOOGLE", "USER", "ACTIVE", now(), now()),
    ("user3@naver.com", "유저3", "password1234", "NAVER", "USER", "ACTIVE", now(), now()),
    ("user4@kakao.com", "유저4", "password1234", "KAKAO", "USER", "ACTIVE", now(), now()),
    ("user5@gmail.com", "유저5", "$2a$10$HJ0EsvgmaY295EpETWBLaem.t5uNJqczTrb09rRGW4Jb23lSkNIre", "TIL", "USER", "ACTIVE", now(), now()),
    ("user6@gmail.com", "유저6", "password1234", "GOOGLE", "USER", "ACTIVE", now(), now()),
    ("user7@naver.com", "유저7", "password1234", "NAVER", "USER", "ACTIVE", now(), now()),
    ("user8@kakao.com", "유저8", "password1234", "KAKAO", "USER", "ACTIVE", now(), now()),
    ("user9@gmail.com", "유저9", "$2a$10$r6iR7iJ/ZwO1/8sbho2Gu.IKTkdvWlzedGI4IOj2eS02iFLlSiEDi", "TIL", "USER", "ACTIVE", now(), now()),
    ("user10@gmail.com", "유저10", "password1234", "GOOGLE", "USER", "ACTIVE", now(), now()),
    ("user11@naver.com", "유저11", "password1234", "NAVER", "USER", "ACTIVE", now(), now()),
    ("user12@kakao.com", "유저12", "password1234", "KAKAO", "USER", "ACTIVE", now(), now());


-- =================== category ===================
INSERT INTO category (tag, created_date, modified_date)
VALUES
    -- 네트워크
    ("네트워크", now(), now()),
    ("HTTP", now(), now()),
    ("OSI 7계층", now(), now()),
    ("Transport 계층", now(), now()),
    -- 데이터베이스
    ("데이터베이스", now(), now()),
    ("트랜잭션", now(), now()),
    ("인덱스", now(), now()),
    -- 자료구조
    ("자료구조", now(), now()),
    ("해시", now(), now()),
    ("이진 탐색 트리", now(), now()),
    ("연결 리스트", now(), now()),
    -- 운영체제
    ("운영체제", now(), now()),
    ("프로세스와 스레드", now(), now()),
    ("동시성", now(), now()),
    ("페이징", now(), now());


-- =================== problem ===================
INSERT INTO problem (id, title, question, solution, grading, level, created_date, modified_date)
VALUES
    (1, 'Array vs LinkedList', '배열(Array)과 연결 리스트(LinkedList)의 데이터 접근 및 데이터 추가/삭제 관점에서 각각의 특성과 장단점을 비교해 주세요.',
     '배열은 메모리에 연속적으로 저장되어 인덱스를 통해 빠르게 접근할 수 있지만, 크기가 고정되어 있고 삽입 및 삭제가 비효율적입니다. 반면, 연결 리스트는 노드들이 포인터로 연결된 구조로, 크기를 동적으로 조정할 수 있고 삽입 및 삭제가 효율적이지만, 인덱스를 통한 직접 접근이 불가능하며 추가적인 메모리를 필요로 합니다.',
     '', 1, now(), now()),
    (2, '객체지향 프로그래밍의 특징', '객체지향 프로그래밍의 대표적인 4가지 특징이 무엇인지 말하고 특징 2개를 골라서 설명해주세요.',
     '객체지향 프로그래밍의 특징으로는 데이터 보호를 위한 캡슐화, 기능을 단순화하는 추상화, 코드 재사용성을 높이는 상속, 다양한 객체의 유연한 동작을 가능하게 하는 다형성이 있습니다.',
     '', 1, now(), now()),
    (3, 'Stack vs Queue', '스택(Stack)과 큐(Queue)의 작동 방식과 주요 차이점을 설명해주세요.',
     '스택은 LIFO(Last In, First Out) 구조로, 마지막에 추가된 요소가 먼저 제거됩니다. 주요 연산으로는 push와 pop이 있습니다. 큐는 FIFO(First In, First Out) 구조로, 먼저 추가된 요소가 먼저 제거됩니다. 주요 연산으로는 enqueue와 dequeue가 있습니다.',
     '', 1, now(), now()),
    (4, '트리 자료구조', '트리(Tree) 자료구조의 특성과 이진 트리(Binary Tree)의 특징을 설명해주세요.',
     '트리는 계층적 구조를 가지며, 노드들이 부모-자식 관계를 가집니다. 이진 트리는 각 노드가 최대 두 개의 자식 노드를 가지는 트리 구조로, 검색, 삽입, 삭제 연산이 효율적입니다.',
     '', 1, now(), now()),
    (5, 'HashMap vs TreeMap', 'HashMap과 TreeMap의 차이점과 각각의 장단점을 설명해주세요.',
     'HashMap은 키-값 쌍을 해시테이블로 구현하여 빠른 검색 속도를 자랑하지만, 순서가 보장되지 않습니다. TreeMap은 키-값 쌍을 이진 검색 트리로 구현하여 순서가 보장되지만, 검색 속도가 상대적으로 느립니다.',
     '', 1, now(), now()),
    (6, '정렬 알고리즘 비교', '버블 정렬(Bubble Sort)과 퀵 정렬(Quick Sort)의 작동 원리와 성능 차이를 설명해주세요.',
     '버블 정렬은 인접한 두 요소를 비교하여 정렬하는 단순한 알고리즘으로, 시간 복잡도는 O(n^2)입니다. 퀵 정렬은 분할 정복 알고리즘을 사용하여 피벗을 기준으로 정렬하며, 평균 시간 복잡도는 O(n log n)입니다.',
     '', 1, now(), now()),
    (7, '그래프 탐색 알고리즘', '깊이 우선 탐색(DFS)과 너비 우선 탐색(BFS)의 작동 방식과 차이점을 설명해주세요.',
     '깊이 우선 탐색(DFS)은 재귀적 혹은 스택을 이용해 그래프의 끝까지 탐색한 후 돌아옵니다. 너비 우선 탐색(BFS)은 큐를 이용해 시작 노드로부터 가까운 노드부터 탐색합니다.',
     '', 1, now(), now()),
    (8, '데이터베이스 정규화', '데이터베이스 정규화의 목적과 정규화 과정의 주요 단계를 설명해주세요.',
     '정규화는 데이터 중복을 최소화하고 데이터 무결성을 유지하기 위해 수행됩니다. 주요 단계로는 1차 정규화(1NF), 2차 정규화(2NF), 3차 정규화(3NF)가 있으며, 각각 특정한 데이터 이상 현상을 방지합니다.',
     '', 1, now(), now()),
    (9, 'API 설계 원칙', 'RESTful API 설계의 기본 원칙과 장점을 설명해주세요.',
     'RESTful API는 클라이언트-서버 구조, 무상태성, 캐시 처리, 계층 구조, 코드 온디맨드 등의 원칙을 따릅니다. 이러한 원칙을 따름으로써 확장성, 성능, 보안, 유연성을 향상시킬 수 있습니다.',
     '', 1, now(), now()),
    (10, '소프트웨어 개발 방법론', '애자일(Agile) 방법론과 폭포수(Waterfall) 방법론의 차이점을 설명해주세요.',
     '애자일 방법론은 유연하고 반복적인 개발 주기를 강조하며, 변화에 빠르게 대응할 수 있습니다. 반면 폭포수 방법론은 단계별로 순차적으로 진행되며, 초기 계획에 따라 엄격하게 진행됩니다.',
     '', 1, now(), now());

-- =================== favorite_problem ===================
INSERT INTO favorite_problem (user_id, problem_id, created_date, modified_date)
VALUES ((SELECT id FROM user WHERE email = "til@gmail.com"), 1, now(), now()),
       ((SELECT id FROM user WHERE email = "til@gmail.com"), 7, now(), now()),
       ((SELECT id FROM user WHERE email = "til@gmail.com"), 10, now(), now());
