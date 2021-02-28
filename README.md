# 서버 to 서버 파일 전송 프로그램

## 1. **환경**
### 1) 개발언어 : JAVA 1.8.0.282
### 2) 개발도구 : Eclipse 4.16(2020-06)
### 3) FrameWork : Spring Boot 2.4.3
### 4) 라이브러리관리 : Gradle 3.0
### 5) DB : H2(in memory), Spring JPA 2.4.5
### 6) 메시징브로커 : kafka 2.7.0
### 7) 전송프로토콜(SFTP서버) : freeFTPd 1.0.13
###	8) 전송프로토콜(SFTP클라이언트) : Spring sftp 5.4.4/jcsh 0.1.55

2. 구현 전략 및 방식
중앙 집중 관리를 위해 서버 to 서버로 구성
고성능을 위하여 비동기로 처리 가능한 곳은 비동기 처리
 -> Kafka로 메시징 처리
 -> 파일전송서버내는 비동기로 모두 처리
고가용성을 위하여 Cluster 구성이 가능한 오픈소스 사용
 -> Kafka, Zookeeper는 Cluster 구성이 가능함
 -> 클라이언트 데몬을 여러개 구동할수 있는 구조로 설계
 
3. 구성
![image](https://user-images.githubusercontent.com/79344232/109420032-aec44e00-7a13-11eb-814a-8e313d745382.png)


4. 구성 상세
![image](https://user-images.githubusercontent.com/79344232/109420943-7ffca680-7a18-11eb-8ff2-834cea75a46e.png)


5. 상세 플로우
![image](https://user-images.githubusercontent.com/79344232/109420925-68252280-7a18-11eb-9cf9-3a6545dc5c51.png)

6. 에러 코드
![image](https://user-images.githubusercontent.com/79344232/109421458-baffd980-7a1a-11eb-9873-8a8d393b3928.png)


6. 주요 소스코드

7. 추가 고려 사항
전송타입
암호화

운영관리
서버정보, 전송정보, 전송로그
재전송 모니터링

8. 시연

* 별첨 1. 테이블 구조
![image](https://user-images.githubusercontent.com/79344232/109421211-bc7cd200-7a19-11eb-957a-1ad0a35a661d.png)

* 별첨 2. 전문 구조
![image](https://user-images.githubusercontent.com/79344232/109421270-f51cab80-7a19-11eb-83e1-1cf26059566e.png)










