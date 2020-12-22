# jpa
## STEP 1
### 요구사항 정리
아래의 DDL(Data Definition Language)을 보고 유추하여 
엔티티 클래스와 리포지토리 클래스를 작성해 본다.
@DataJpaTest를 사용하여 학습 테스트를 해 본다. 

~~~
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
~~~
~~~
create table station (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    name varchar(255),
    primary key (id)
) engine=InnoDB

alter table station 
    add constraint UK_gnneuc0peq2qi08yftdjhy7ok unique (name)
~~~
~~~
create table member (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    age integer,
    email varchar(255),
    password varchar(255),
    primary key (id)
) engine=InnoDB
~~~
~~~
create table favorite (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine=InnoDB
~~~
### 구현 내용 정리
* line, station, member, favorite 엔티티 작성
* RepositoryTest 코드 작성 & Repository 작성
---
## STEP2
### 요구사항 정리
* 지하철역과 노선이 있다.
* 지하철역은 여러 노선에 소속될 수 있다. 
* 환승역을 고려한다.
* 노선 조회 시 속한 지하철역을 볼 수 있다.
* 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.
* 사용자와 즐겨찾기가 있다.
* 사용자는 여러 즐겨찾기를 가질 수 있다.
* 즐겨찾기에는 출발역과 도착역이 포함되어 있다.

### 구현 내용 정리
* station <-> line (다대다 양방향) 
* favorite <-> member (다대일 양방향) : 테스트 & repository
* favorite -> station (일대일 단방향) : 테스트 & repository  
* favorite : 속성 - 출발역, 도착역 
* 노선 조회시 속한 지하철역 확인
* 지하철역 조회시 속한 노선 확인
---
## STEP3
### 요구사항 정리
* 환승역 고려해서 manytomany 관계 맺기
* 노선에 역을 추가할 때 길이 값 입력

### 구현 내용 정리
* station <-> line (다대다 양방향) : 연결 엔티티를 사용.
* 추가적인 길이를 필드값으로 입력해서 매핑시 받아주도록 처리. 

