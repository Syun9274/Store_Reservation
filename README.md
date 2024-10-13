# 매장 테이블 예약 서비스

`매장을 방문할 때 미리 방문 예약을 진행하는 기능`을 구현하는 것을 목표로 한다.

## Stack
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=spring&logoColor=white)

![Spring Boot Starter Data JPA](https://img.shields.io/badge/Spring%20Boot%20Data%20JPA-6DB33F?logo=spring&logoColor=white)

![Spring Boot Starter Security](https://img.shields.io/badge/Spring%20Boot%20Security-6DB33F?logo=spring&logoColor=white)

![Lombok](https://img.shields.io/badge/Lombok-FFA500?logo=lombok&logoColor=white)

![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)

## 요구되는 기능

- **회원가입**: 매장 점장 및 일반 사용자는 예약을 위해 회원가입을 해야 합니다.
- **매장 신규 등록**: 점장은 상점 정보를 등록하고 관리할 수 있습니다.
- **매장 예약**: 사용자들은 매장을 예약할 수 있습니다.
- **매장 도착 확인**: 예약 시간에 맞추어 키오스크를 통해 도착을 확인합니다.
- **이용 후 매장 리뷰 작성**: 서비스 이용 후 리뷰를 작성하여 피드백을 남깁니다.

## 프로젝트 설명 및 시나리오

### 공통 인증 시스템
- 모든 사용자와 매장 점장은 공통 인증 시스템을 통해 인증을 받습니다.

### 매장 등록 및 파트너 회원 가입
- 매장의 점장은 예약 서비스 앱에 상점을 등록할 수 있습니다. 
  - 등록 시 필요한 정보는 상점 명, 상점 위치, 상점 설명입니다.
- 매장을 등록하기 위해서는 파트너 회원 가입이 필수입니다. 
  - 가입 절차는 간단하며, 승인 조건 없이 가입 후 바로 이용 가능합니다.

### 매장 이용 및 예약 절차
- 매장 이용자는 앱을 통해 매장을 검색하고, 상세 정보를 확인할 수 있습니다.
- 매장의 상세 정보를 확인한 후 예약을 진행할 수 있습니다. 예약을 진행하려면 일반 회원 가입이 필수입니다.

### 방문 확인 및 리뷰 작성
- 예약한 이후, 예약 시간 10분 전에 매장에 도착하여 키오스크를 통해 방문 확인을 진행합니다.
- 예약 서비스 이용 후, 매장에 대한 리뷰를 작성할 수 있습니다.