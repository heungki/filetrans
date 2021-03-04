# 서버 to 서버 파일 전송 프로그램
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fheungki%2Ffiletrans&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
## 1. **환경**
### 1) 개발언어 : JAVA 1.8.0.282
### 2) 개발도구 : Eclipse 4.16(2020-06)
### 3) FrameWork : Spring Boot 2.4.3
### 4) 라이브러리관리 : Gradle 3.0
### 5) DB : H2(in memory), Spring JPA 2.4.5
### 6) 메시징브로커 : kafka 2.7.0
### 7) 전송프로토콜(SFTP서버) : freeFTPd 1.0.13
###	8) 전송프로토콜(SFTP클라이언트) : Spring sftp 5.4.4/jcsh 0.1.55

## 2. 구현 전략 및 방식
### 1) 중앙 집중 관리를 위해 서버 to 서버로 구성
### 2) 고성능을 위하여 비동기로 처리 가능한 곳은 비동기 처리
###    -> Kafka로 메시징 처리
###    -> 파일전송서버내는 비동기로 모두 처리
### 3) 고가용성을 위하여 Cluster 구성이 가능한 오픈소스 사용
###    -> Kafka, Zookeeper는 Cluster 구성이 가능함
###    -> 클라이언트 데몬을 여러개 구동할수 있는 구조로 설계
 
## 3. 구성

![image](https://user-images.githubusercontent.com/79344232/109420032-aec44e00-7a13-11eb-814a-8e313d745382.png)


## 4. 구성 상세

![image](https://user-images.githubusercontent.com/79344232/109420943-7ffca680-7a18-11eb-8ff2-834cea75a46e.png)


## 5. 상세 플로우

![image](https://user-images.githubusercontent.com/79344232/109420925-68252280-7a18-11eb-9cf9-3a6545dc5c51.png)

## 6. 에러 코드

![image](https://user-images.githubusercontent.com/79344232/109421458-baffd980-7a1a-11eb-9873-8a8d393b3928.png)


## 7. 주요 소스코드
1) 배치 파일 송신

![image](https://user-images.githubusercontent.com/79344232/109422074-5bef9400-7a1d-11eb-8fd4-bbfe2f6b12b4.png)

2) 전송 파일

![image](https://user-images.githubusercontent.com/79344232/109422155-b2f56900-7a1d-11eb-9c33-170ddd96a758.png)

3) 전송 데몬(송신)

![image](https://user-images.githubusercontent.com/79344232/109422088-6d38a080-7a1d-11eb-91a4-db26b0c436ba.png)
![image](https://user-images.githubusercontent.com/79344232/109422094-76297200-7a1d-11eb-8315-221bc2a06c61.png)

4) 전송 데몬(수신)

![image](https://user-images.githubusercontent.com/79344232/109422125-8ccfc900-7a1d-11eb-9b54-37050107a28f.png)
![image](https://user-images.githubusercontent.com/79344232/109422133-922d1380-7a1d-11eb-9af4-0d2183c3f9a5.png)

5) 전송 타켓

![image](https://user-images.githubusercontent.com/79344232/109422176-cacced00-7a1d-11eb-864e-33f3fb6acfe5.png)

6) 전송 결과

![image](https://user-images.githubusercontent.com/79344232/109422187-d4565500-7a1d-11eb-87a3-d4341a8d472e.png)


## 8. 추가 고려 사항

![image](https://user-images.githubusercontent.com/79344232/109421801-2eeeb180-7a1c-11eb-9a50-6e9d3f36c390.png)


## 9. 시연
Demo.zip 다운로뒤 동봉된 설치가이드에 따라 진행

## *별첨 1. 테이블 구조
* 
![image](https://user-images.githubusercontent.com/79344232/109421211-bc7cd200-7a19-11eb-957a-1ad0a35a661d.png)

## *별첨 2. 전문 구조
* 
![image](https://user-images.githubusercontent.com/79344232/109421270-f51cab80-7a19-11eb-83e1-1cf26059566e.png)










