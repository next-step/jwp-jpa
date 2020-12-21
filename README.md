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

### Todo-list(폐기)
- [X] 식별자 생성 방식을 Identity로 변경
  - 식별자 생성 방식을 기본으로 지정할 경우 키 생성에 대한 DB 의존성이 존재하지 않음.
  - 이 때문에 Spring Data JPA를 통해 save를 하더라도 트랜잭션이 끝날 때까지 insert 쿼리가 동작하지 않음.
  - 하지만 Identity로 생성방식을 지정하면, DB 의존성이 생기기 때문에 Spring Data JPA로 save를 하는 즉시 insert 쿼리가 동작함.
    - 영속성 컨텍스트를 통해 관리하려면 ID 식별자가 필수이기 때문
  - Unique Constrain이 걸린 대상을 중복해서 저장할 때 어디에서 예외가 발생하는지를 보고 확인할 수 있다.
- [X] Auditing을 기능을 이용해보기
  - ID의 경우 단순 반복 외에 사용되는 상황이 있을 수 있으므로 공동 분리 보류
- [X] 지하쳘역, 노선의 다대다 관계 매핑하기 (환승역 고려 때문에 다대다 관계)
  - [X] ManyToMany 매핑을 진행하고 문제점 경험하기
    - 생각보다 매핑 자체는 쾌적하게 잘 진행된다.
    - 하지만 과하게 복잡한 SQL이 발생한다.
    - 양방향 매핑이 되기 때문에 객체 수정, 삭제에 많은 주의를 기울여야 한다.
    - 가장 큰 문제는 중간관계 테이블을 손댈 수가 없기 때문에 지하철 구간의 거리와 같은 정보를 추가하는 게 불가능하다.(by. 영한님)
    - 특히 현재 상황에서 가장 큰 문제는 Line에 등록된 Station들의 방향성을 관리할 수가 없다.
      - 해당 관계를 Line, Station 그 어느 곳에서도 감당하기 어렵다.
    - 다대다의 중간관계 매핑 테이블은 그 자체로도 이미 충분한 의미를 갖고 있을 수 있기 때문에 복잡한 실제 도메인 환경에서는 사용하기 어렵다.
  - [X] 지하철역, 노선의 중간 오브젝트를 추출하고 1:N, N:1로 매핑해보기
    - 연관관계는 잘 되지만, 중간 오브젝트(LineStation)가 연관관계 주인이 되면서 객체 탐색에 제한이 많이 생긴다.
    - 특히, 업데이트나 삭제 동작 때마다 해당 객체가 영속 컨텍스트에 들어있는지 신경써야 했다.
    - 뿐만 아니라 양방향 오브젝트 탐색이 필요할 때는 연관관계 편의 메서드를 만드는 등 여러가지 불편함이 있다.
  - [X] 일급 컬렉션을 이용해서 VO 컬렉션으로 연관관계 매핑해보기
    - 제이슨과 DM을 통해 질의응답하던 중 연관관계 중간 오브젝트가 사실은 VO 컬렉션이 아닌지 검토해 볼 필요가 있다는 조언을 얻음
    - Station은 아예 별도로 존재하는 엔티티로 둔다
    - 사실 Line이 관심있는 건 Station 보다도 정확하게는 `중간 객체인 구간 컬렉션`이다.
    - Line에서 필요한 건 단순히 상행역, 하행역, 거리로 이루어진 `값`이 필요하다.
    - 가장 큰 증거는 Line 없이 존재하는 구간(Section)은 존재할 수 없다.
    - 이 구간(Section)이 여러개 필요하기 때문에 VO 컬렉션으로 볼 수 있고, 일급 컬렉션을 통해 구현이 가능할 것이라 생각
    - [VO 컬렉션 참고자료](http://redutan.github.io/2018/05/29/ddd-values-on-jpa)
    - `역에서 Line을 조회한다` 라는 요구사항이 있기 때문에 VO 컬렉션으로는 구현이 어려움
    - Station 또한 Aggregate로 서로 연관관계에 관심을 갖고 탐색하기 때문
    - VO 컬렉션으로 구현할 경우 Station에서 Line을 접근하기 위해 DomainService가 필요하고 과도하게 복잡한 도메인이 될 소지가 있음
- [ ] 사용자, 즐겨찾기, 역 관계 매핑하기
  - [ ] 사용자와 즐겨찾기는 1:N 관계로 매핑을 진행한다.
    - [ ] 연관관계 주인(관리자) 필드를 고려하고 진행해본다.
  - [ ] 즐겨찾기와 출발역, 도착역도 1:N 관계로 매핑을 진행한다.
    - [ ] VO 컬렉션으로 연관관계를 매핑해본다.

### Todo-list (최종)
- [X] Line, Station 다대다 관계 설정하기
  - [X] 중간 객체 LineStation을 이용해서 관계 설정하기
    - 결정 사유
      - VO 컬렉션을 사용할 경우 Station에서 Line을 조회하는 요구사항을 충족시키기 위해 과하게 복잡한 설계를 진행해야 함
      - 현재 요구사항에 맞는 만큼 구현하기 위해 최대한 간단한 구현방법 선택
    - Line이 도메인 개념상 LineStation의 부모(Aggregate Root)로 활동하도록 구현
    - Station은 단순히 양방향 관계로 LineStation을 통해 속한 Line이 어딘지 조회만 가능
- [X] Member, Favortie 다대다 관계 설정하기
  - [X] VO Collection을 이용해서 관계 설정하기
    - 결정 사유
      - 단방향 조회 조건만 있고 Favorite에서 같은 타입의 엔티티를 두개 갖는 상황이기 때문에 연관관계를 끊어 주는 게 관리하기 더 쉬운 상황
      - 설계적으로 VO 컬렉션이 더 적합하다고 판단되어 선택

## Step2. 리뷰 반영
- [X] IdentifiedValueObject를 좀더 적합한 이름으로 변경
- [X] 매핑시 불필요한 기본 옵션 제거
- [X] 지나치게 장황한 테스트 메서드에서 공통 부분 추출하기

## Step3. 다대다 연관 관계 리팩토링
- [X] @ManyToMany로 `Station`, `Line` 구성해보기
  - 배운점
    - ManyToMany는 자동으로 테이블을 구성하기 때문에 둘의 연관관계를 맺는 개념에 별도의 속성을 부여할 수 없다.
    - 마찬가지로 자동으로 생성하기 때문에 연관되는 오브젝트끼리 다른 의미를 갖고 여러개의 참조를 만들 수 없다.
