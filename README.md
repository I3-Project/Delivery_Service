# DELIVER_SERVICE 🏍


## 🗓️ 개발 기간
* 2024.11.08 ~ 2024.11.18


## 📚 목차
[1. 프로젝트 개요](#1-프로젝트-개요)

[2. 역할 분담](#2-역할-분담)

[3. 요구사항 명세서](#3-요구사항-명세서)

[4. API 명세서](#4-api-명세서)

[5. ERD](#5-erd)

[6. 기술 스택](#6-기술-스택)

[7. 인프라 설계도](#7-인프라-설계도)

[8.Git Convention](#8-Git-Convention)


## ✅ 1. 프로젝트 개요
* **주제:** 음식 주문 및 결제 플랫폼
* **목표:** 운영 가게 및 음식 재고 관리와 주문, 결제 + 내역 관리를 제공하는 플랫폼 구현


## 👊🏻 2. 역할 분담
| 이름                                         | 역할 분담                        |
|--------------------------------------------|------------------------------|
| 신상우      | User, Auth&Jwt, Cart        |
| 민지수      | Order, Payment, Review, Aws, RDS        |
| 김한준      | Category, Store, Product, Google Api  |


## 📕 3. 요구사항 명세서
* [📘 배딜핑 - 요구사항 명세서 및 테이블 설계서](https://docs.google.com/spreadsheets/d/1gQWuJSk7CjLbx0QzEvXpc8jvOHdpER1mDXA7hXSqsc0/edit?usp=sharing)


## 📙 4. API 명세서
- PostMan을 활용한 API 테스트 진행, 이를 문서화하여 API 명세서 작성
* [API 명세서 - Notion](https://docs.google.com/spreadsheets/d/1gQWuJSk7CjLbx0QzEvXpc8jvOHdpER1mDXA7hXSqsc0/edit?gid=0#gid=0)


## 📋 5. ERD
![ERD](./IMG/erd.png)


## 🛠️ 6. 기술 스택
* Backend
    * Spring Boot 3.3.5
    * Spring Cloud Gateway
    * Gradle
    * JWT
    * Oauth2
    * QueryDSL
    * JPA
* API Test
    * PostMan
* Database
    * Postgresql
* Infra
    * AWS EC2 t2.medium
    * RDS
* CI/CD
    * GitAction
* Version
    * Git
    * Github

 
## 🛠️ 7. 인프라 설계도
![인프라설계도](./IMG/sys-arch.png)


## 💡 8.Git Convention

|머리말|내용|
|-----|-----|
|Init|시작|
|Fix|버그 수정|
|Add|새로운 기능 추가|
|Update|기존 기능 업데이트|
|Remove|불필요한 코드 제거|
|Refactor|코드 리팩토링|
|Improve|성능 개선|
|Document|문서화|
|Test|테스트 추가 또는 수정|
