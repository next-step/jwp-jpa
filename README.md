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
