# JPA

## 요구사항

아래의 도메인 설명을 보고 엔티티 클래스의 연관 관계를 매핑해 본다.

- 지하철역과 노선이 있다.  
- 지하철역은 여러 노선에 소속될 수 있다.  
    - 환승역을 고려한다.  
- 노선 조회 시 속한 지하철역을 볼 수 있다.  
- 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.  
- 사용자와 즐겨찾기가 있다.  
- 사용자는 여러 즐겨찾기를 가질 수 있다.  
- 즐겨찾기에는 출발역과 도착역이 포함되어 있다.  

아래의 DDL(Data Definition Language)을 보고 유추하여 엔티티 클래스와 리포지토리 클래스를 작성해 본다. 
@DataJpaTest를 사용하여 학습 테스트를 해 본다.

> create table line (  
id bigint not null auto_increment,  
created_date datetime(6),  
modified_date datetime(6),  
color varchar(255),  
name varchar(255),  
primary key (id)  
) engine=InnoDB  
alter table line add constraint UK_9ney9davbulf79nmn9vg6k7tn unique (name)  

> create table station (  
id bigint not null auto_increment,  
created_date datetime(6),  
modified_date datetime(6),  
name varchar(255),  
primary key (id)  
) engine=InnoDB  
alter table station add constraint UK_gnneuc0peq2qi08yftdjhy7ok unique (name)

> create table member (  
id bigint not null auto_increment,  
created_date datetime(6),  
modified_date datetime(6),  
age integer,  
email varchar(255),  
password varchar(255),  
primary key (id)  
) engine=InnoDB  

> create table favorite (  
id bigint not null auto_increment,  
created_date datetime(6),  
modified_date datetime(6),  
primary key (id)  
) engine=InnoDB

### 힌트

> class StationRepositoryTest {  
 @Autowired  
 private StationRepository stations;  
>
>    @Test  
   void save() {  
       Station expected = new Station("잠실역");  
       Station actual = stations.save(expected);  
       assertAll(  
               () -> assertThat(actual.getId()).isNotNull(),  
               () -> assertThat(actual.getName()).isEqualTo(expected.getName())  
       );  
   }  
>
>   @Test  
   void findByName() {  
       String expected = "잠실역";  
       stations.save(new Station(expected));  
       String actual = stations.findByName(expected).getName();  
       assertThat(actual).isEqualTo(expected);  
   }  
}  

Spring Data JPA 사용 시 아래 옵션은 동작 쿼리를 로그로 확인할 수 있게 해준다.

> spring.jpa.properties.hibernate.format_sql=true  
spring.jpa.show-sql=true  

H2 데이터베이스를 사용한다면 아래의 프로퍼티를 추가하면 MySQL Dialect을 사용할 수 있다.

> spring.datasource.url=jdbc:h2:~/test;MODE=MySQL  
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect  
