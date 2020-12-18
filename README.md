# jpa

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
