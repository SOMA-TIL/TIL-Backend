# Docker
## [Dockerfile](./Dockerfile)
Dockerfile을 활용해 지정한 모듈에 해당하는 도커 이미지를 생성할 수 있음

#### 도커 이미지 빌드 방법
>  전제 조건 : 모듈이 빌드되어 jar 파일이 생성되어 있어야 함
- 필요한 인자(PROFILE, PROJECT_NAME)를 설정한 후 아래 명령어 실행
- Mac에서 build한 이미지를 ubuntu에 띄울 때는 --platform linux/amd64 붙여줘야 함
```bash
# 명령어 실행 위치 : 프로젝트 최상위 디렉토리
docker build -t ${빌드할 이미지 이름} \
  --build-arg PROFILE=${} \
  --build-arg PROJECT_NAME=${빌드할 모듈 이름} \
  -f docker/Dockerfile .
  
 # example
 docker build -t somatil/til-api \
  --build-arg PROFILE=dev \
  --build-arg PROJECT_NAME=til-api \
  -f docker/Dockerfile .
```

<br><br>

## [dev-app.yml](./dev-app.yml)
개발 서버에서 여러 모듈을 한번에 실행할 수 있도록 docker-compose 파일

<br><br>


## [dev-infra.yml](./dev-infra.yml)
개발 환경에서 사용할 인프라 구성을 정의한 docker-compose 파일
