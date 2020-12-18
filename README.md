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
- [VO 컬렉션 참고자료](http://redutan.github.io/2018/05/29/ddd-values-on-jpa)

### Todo-list
- [X] 식별자 생성 방식을 Identity로 변경
  - 식별자 생성 방식을 기본으로 지정할 경우 키 생성에 대한 DB 의존성이 존재하지 않음.
  - 이 때문에 Spring Data JPA를 통해 save를 하더라도 트랜잭션이 끝날 때까지 insert 쿼리가 동작하지 않음.
  - 하지만 Identity로 생성방식을 지정하면, DB 의존성이 생기기 때문에 Spring Data JPA로 save를 하는 즉시 insert 쿼리가 동작함.
    - 영속성 컨텍스트를 통해 관리하려면 ID 식별자가 필수이기 때문
  - Unique Constrain이 걸린 대상을 중복해서 저장할 때 어디에서 예외가 발생하는지를 보고 확인할 수 있다.
- [X] Auditing을 기능을 이용해보기
  - ID의 경우 단순 반복 외에 사용되는 상황이 있을 수 있으므로 공동 분리 보류
- [ ] 지하쳘역, 노선의 다대다 관계 매핑하기 (환승역 고려 때문에 다대다 관계)
  - [X] ManyToMany 매핑을 진행하고 문제점 경험하기
    - 생각보다 매핑 자체는 쾌적하게 잘 진행된다.
    - 하지만 과하게 복잡한 SQL이 발생한다.
    - 양방향 매핑이 되기 때문에 객체 수정, 삭제에 많은 주의를 기울여야 한다.
    - 가장 큰 문제는 중간관계 테이블을 손댈 수가 없기 때문에 지하철 구간의 거리와 같은 정보를 추가하는 게 불가능하다.(by. 영한님)
    - 다대다의 중간관계 매핑 테이블은 그 자체로도 이미 충분한 의미를 갖고 있을 수 있기 때문에 복잡한 실제 도메인 환경에서는 사용하기 어렵다.
  - [ ] 지하철역, 노선의 중간 오브젝트를 추출하고 1:N, N:1로 매핑해보기
  - [ ] 일급 컬렉션을 이용해서 VO 컬렉션으로 연관관계 매핑해보기
- [ ] 사용자, 즐겨찾기, 역 관계 매핑하기
  - [ ] 사용자와 즐겨찾기는 1:N 관계로 매핑을 진행한다.
    - [ ] 연관관계 주인(관리자) 필드를 고려하고 진행해본다.
  - [ ] 즐겨찾기와 출발역, 도착역도 1:N 관계로 매핑을 진행한다.
    - [ ] VO 컬렉션으로 연관관계를 매핑해본다.
