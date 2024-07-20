# TIL-Backend
## Tech Stack
> - **Java** 17
> - **Spring Boot** 3.3
> - **Gradle**
> - **JPA**
> - **MySQL**
> - **Redis**
> - **Docker**

<br>

## Getting Started
### 프로젝트 clone
> 프로젝트 clone 시 서브 모듈까지 함께 clone  
> (단, 서브 모듈은 권한이 있는 경우에만 clone 가능)
```bash
git clone --recursive https://github.com/SOMA-TIL/TIL-Backend.git
# or
git clone --recursive git@github.com:SOMA-TIL/TIL-Backend.git
```

<br>


### 로컬 개발 환경 도커 컨테이너 관리
> MySQL, Redis 등의 인프라 서비스를 도커 컨테이너로 관리
#### 1. 도커 컨테이너 실행
```bash
docker-compose up # 포그라운드 실행(로그 실시간 출력)
# or
docker-compose up -d # 백그라운드 실행
```

#### 2. 도커 컨테이너 종료
```bash
docker-compose down
# or
docker rm -f $(docker ps -qa) # 실행중인 모든 컨테이너 종료
```

#### 3. [기타](https://soma-til.notion.site/docker-compose-825a0b390a0b470baf6afd87e3213f73)

<br>

### 빌드 및 실행
> 명령어 입력 위치는 프로젝트의 최상단(root) 디렉토리 기준  
> 서브 모듈은 til-api, 프로필은 local 기준으로 작성
#### 1. 빌드
```bash
# 전체 프로젝트 빌드
# ./gradlew build -Pprofile=${프로필}
./gradlew build -Pprofile=local

# 특정 서브 모듈만 빌드
# ./gradlew :{서브 모듈명}:bootJar -Pprofile=${프로필}
./gradlew :til-api:bootJar -Pprofile=local
```

#### 2. 실행
```bash
# bootRun으로 실행
# ./gradlew :{서브 모듈명}:bootRun -Pprofile=${프로필}
./gradlew :til-api:bootRun -Pprofile=local

# java -jar로 실행
# java -jar {서브 모듈명}/build/libs/{생성된 파일명}.jar --spring.profiles.active=${프로필} --spring.config.location=${프로필별 설정 파일 경로}
java -jar til-api/build/libs/til-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=local --spring.config.location=./configs/api/
```
