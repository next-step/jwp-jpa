# jpa

* 아래의 DDL을 보고 유추하여 엔티티 클래스와 리포지토리 클래스 작성
* `@DataJpaTest` 를 통해 학습 테스트

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
create table station (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    name varchar(255),
    primary key (id)
) engine=InnoDB

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
create table favorite (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    primary key (id)
) engine=InnoDB

```
