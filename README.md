# TIL-Backend

### 로컬 개발 환경 도커 컨테이너 관리
1. 도커 컨테이너 실행
    ```bash
    docker-compose up -d # 백그라운드 실행
    ```
2. 도커 컨테이너 종료
    ```bash
   docker-compose down
   
   # or
   
   docker rm -f $(docker ps -qa)
    ```
3. [기타](https://soma-til.notion.site/docker-compose-825a0b390a0b470baf6afd87e3213f73)

<br>

### 빌드 및 실행

1. 빌드
    ```bash
    # 전체 프로젝트 빌드
    ./gradlew build -Pprofile=${프로필}
    
    # 특정 서브 모듈만 빌드
    ./gradlew :{서브 모듈명}:bootJar -Pprofile=${프로필}
    ```
2. 실행
    ```bash
    # bootRun으로 실행
    ./gradlew :{서브 모듈명}:bootRun -Pprofile=${프로필}
    
    # java -jar로 실행
    java -jar {서브 모듈명}/build/libs/{생성된 파일명}.jar --spring.profiles.active=${프로필} --spring.config.location=./configs/api/
    ```
