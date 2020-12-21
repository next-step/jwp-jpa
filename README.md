# jpa

## H2 설정
* H2 사용시 MySQL Dialect 사용 설정
  * application.yaml(yml) 파일 설정
    * spring:
      * datasource:
        * hikari:
          * jdbc-url: jdbc:h2:tcp://localhost/~/test;MODE=MYSQL
    * properties:
      * hibernate:
        * dialect: org.hibernate.dialect.MySQL57Dialect
* 테스트 시 H2 사용할 때 test/resources 경로에 application 설정을 하지 않는 경우 아래 어노테이션 추가
  * 테스트 클래스에 @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
