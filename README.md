# jpa


## 1단계 - 엔티티 매핑
#### 기능 요구사항
DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
@DataJpaTest를 사용하여 학습 테스트를 해 본다.

- [X] 프로젝트 설정 및 환경 구성
* build.gradle
* application.properties

- [X] 공통 부분 엔티티 추출 (BaseTimeEntity)
* created_date
* modified_date

- [X] Line 엔티티 클래스 생성 및 테스트

- [X] Station 엔티티 클래스 생성 및 테스트

- [X] Member 엔티티 클래스 생성 및 테스트

- [X] Favorite 엔티티 클래스 생성 및 테스트


## 2단계 - 연관 관계 매핑
#### 기능 요구사항
도메인 설명을 보고 엔티티 클래스의 연관 관계를 매핑해 본다.

- [ ] 지하철역(Station) 과 노선(Line)
* Many to Many
    * 지하철역은 여러 노선에 소속될 수 있다.
    * 환승역을 고려한다.
    * 하나의 노선에 여러 지하철역이 소속될 수 있다.
    * 지하철역 조회 시 어느 노선들에 속한지 볼 수 있다.
    * 노선 조회 시 속한 지하철역을 볼 수 있다.


- [ ] 사용자(Member) 와 즐겨찾기(Favorite)
* One to Many
    * 사용자는 여러 즐겨찾기를 가질 수 있다.

- [ ] 즐겨찾기(Favorite) 와 출발역(Station), 도착역(Station)
* 각각 Many to One
    * 즐겨찾기에는 출발역과 도착역이 포함되어 있다.