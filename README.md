# jpa

요구 사항
### STEP1 
아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다. @DataJpaTest를 사용하여 학습 테스트를 해 본다.
``` sql
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
``` sql
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
``` sql
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
``` sql
create table favorite (
id bigint not null auto_increment,
created_date datetime(6),
modified_date datetime(6),
primary key (id)
) engine=InnoDB
```

### STEP2
아래의 도메인 설명을 보고 엔티티 클래스의 연관 관계를 매핑해 본다.

- 지하철역과 노선이 있다.
- 지하철역은 여러 노선에 소속될 수 있다.
    - 환승역을 고려한다.
- 노선 조회 시 속한 지하철역을 볼 수 있다.
- 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.
- 사용자와 즐겨찾기가 있다.
- 사용자는 여러 즐겨찾기를 가질 수 있다.
- 즐겨찾기에는 출발역과 도착역이 포함되어 있다.