# jpa

## Step1. 엔티티 매핑
- Todo-list
- [ ] 아래 테이블을 참고해서 JPA 엔티티를 생성한다.
- Line
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
- Station
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
- Member
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
- favorite
```sql
create table favorite (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine=InnoDB
```
- [X] 각 엔티티별로 기본적인 CRUD 테스트를 진행한다.
- [X] 영속성 컨텍스트를 통한 동등성 보장에 대해 학습 테스트를 진행한다.

## Step1 리뷰 반영기
- [X] CRUD로 뭉뚱그려진 단위 테스트를 메서드 단위로 세분화 시키기
    - [X] Favorite
    - [X] Station
    - [X] Member
- [X] 좀 더 안전한 객체 생성을 위해 인자가 없는 생성자의 접근 제어 레벨 바꾸기

## Step2. 연관 관계 매핑
- 다양한 연관 관계를 맺어보고 실습하기

### Todo-list
- [ ] 식별자 생성 방식을 Identity로 변경
- [ ] Auditing을 기능을 이용해보기
- [ ] 지하쳘역, 노선의 다대다 관계 매핑하기 (환승역 고려 때문에 다대다 관계)
  - [ ] ManyToMany 매핑을 진행하고 문제점 경험하기
  - [ ] 연관 관계 매핑 오브젝트를 활용해서 ManyToMany 매핑해보기
  - [ ] 일급 컬렉션을 이용해서 VO 컬렉션으로 연관관계 매핑해보기
- [ ] 사용자, 즐겨찾기, 역 관계 매핑하기
  - [ ] 사용자와 즐겨찾기는 1:N 관계로 매핑을 진행한다.
    - [ ] 연관관계 주인(관리자) 필드를 고려하고 진행해본다.
  - [ ] 즐겨찾기와 출발역, 도착역도 1:N 관계로 매핑을 진행한다.
    - [ ] VO 컬렉션으로 연관관계를 매핑해본다.
