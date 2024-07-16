insert into user (email, nickname, password, platform, role, status, created_date, modified_date)
values
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
