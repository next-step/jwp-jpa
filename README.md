# jpa

## 1단계 - 엔티티 매핑

### 요구사항
* 아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다. @DataJpaTest를 사용하여 학습 테스트를 해 본다.

```sql
create table line (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    color varchar(255),
    name varchar(255),
    primary key (id)
) engine=InnoDB

alter table line
    add constraint UK_9ney9davbulf79nmn9vg6k7tn unique (name)
```

```sql
create table station (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    name varchar(255),
    primary key (id)
) engine=InnoDB

alter table station 
    add constraint UK_gnneuc0peq2qi08yftdjhy7ok unique (name)
```

```sql
create table member (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    age integer,
    email varchar(255),
    password varchar(255),
    primary key (id)
) engine=InnoDB
```

```sql
create table favorite (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine=InnoDB
```

### 기능 구현 리스트
* 엔티티 구현
    * Line (name 유니크 키)
    * Station (name 유니크 키)
    * Member
    * Favorite
* 테스트
    * Line 등록
    * Line 조회
    * Station 등록
    * Station 조회
    * Member 등록
    * Member 조회

## 2단계 - 연관 관계 매핑

### 요구사항
* 지하철역과 노선이 있다.
* 지하철역은 여러 노선에 소속될 수 있다.
  * 환승역을 고려한다.
* 노선 조회 시 속한 지하철역을 볼 수 있다.
* 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.
* 사용자와 즐겨찾기가 있다.
* 사용자는 여러 즐겨찾기를 가질 수 있다.
* 즐겨찾기에는 출발역과 도착역이 포함되어 있다.

### 기능 구현 리스트
* 1단계 피드백 반영
  * 중복되는 필드 BaseEntity 추가하여 상속
* LineStation 엔티티 추가 (환승역 고려 다대다 매핑)
* 엔티티 연관관계 추가
  * LineStation -> Line
  * LineStation -> Station 
  * Member 엔티티 OneToMany -> Favorite 엔티티
  * Favorite 엔티티 ManyToOne -> Member 엔티티
* 즐겨찾기에 출발역과 도착역 필드 추가
* 테스트
  * Line 조회시 속한 역들 정보 확인
  * Station 조회시 어느 노선에 속한지 확인
  * 사용자 조회시 가지고 있는 즐겨찾기 확인
  * 즐겨찾기 조회시 사용자 확인
  * 즐겨찾기 조회시 출발역 도착역 포함 확인
