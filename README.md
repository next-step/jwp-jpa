# jpa

## Step 1 - 엔티티 매핑 

### 요구사항 
- 아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다. @DataJpaTest를 사용하여 학습 테스트를 해 본다.
```text
create table line (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    color varchar(255),
    name varchar(255),
    primary key (id)
) engine=InnoDB
```
```text
alter table line
    add constraint UK_9ney9davbulf79nmn9vg6k7tn unique (name)
create table station (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    name varchar(255),
    primary key (id)
) engine=InnoDB
```
```text
alter table station 
    add constraint UK_gnneuc0peq2qi08yftdjhy7ok unique (name)
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
```text
create table favorite (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine=InnoDB
```

## Step 2 - 연관 관계 매핑

### 요구사항
아래의 도메인 설명을 보고 엔티티 클래스의 연관 관계를 매핑해 본다.

- 지하철역과 노선이 있다.
- 지하철역은 여러 노선에 소속될 수 있다.
    - 환승역을 고려한다.
- 노선 조회 시 속한 지하철역을 볼 수 있다.
- 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.
- 사용자와 즐겨찾기가 있다.
- 사용자는 여러 즐겨찾기를 가질 수 있다.
- 즐겨찾기에는 출발역과 도착역이 포함되어 있다.

## 연관 관계
- ~~지하철역(n) : 노선(n) = 양방향 연관관계~~

- 지하철역 노선(n) : 지하철역(1) = 다대일 양방향 연관관계
    - [X] 지하철역은 여러 노선에 소속될 수 있다.
    - [X] 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.

- 지하철역 노선(n) : 노선(1) = 다대일 양방향 연관관계
    - [X] 노선 조회 시 속한 지하철역을 볼 수 있다.
    
- 즐겨찾기(n) : 사용자(1) = 다대일 양방향 연관 관계
    - [X] 사용자는 여러 즐겨찾기를 가질 수 있다.
    - 일대다 단방향 보다는 다대일 양방향 관게를 사용.
    
- ~~즐겨찾기(n) : 지하철역(n) = 다대다 단뱡향 연관 관계~~

- 즐겨찾기(n) : 지하철역(1) = 다대일 단방향 연관관계
    - [X] 즐겨찾기에는 출발역과 도착역이 포함되어 있다.
    
## Step 3 - 다대다 연관 관계 리팩터링

### 요구사항
- 노선에 역을 추가할 때는 이전 역과 얼마나 차이가 나는지 길이(distance)를 알고 있어야 한다.
