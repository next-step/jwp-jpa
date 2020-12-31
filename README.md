# JPA
## 1단계 - 엔티티 매핑
- 엔티티 클래스 개발
  - 공통 필드는 MappedSuperclass 뺄 것
- 리포지토리 클래스 개발
  - 도메인 패키지에 같이 두기
  - 인터페이스만 만들기  
- @DataJpaTest 학습테스트
  - CRUD 테스트

## 2단계 - 연관 관계 매핑
- Line <> Station 다대다 관계 설정
- Line <> Station 양방향 관계 설정
- Favorite <> Member 다대일 관계 설정
- Favorite <> Station 다대일 관계 설정

## 3단계 - 다대다 연관관계 리팩토링
- 다대다를 일대다, 다대일로 리팩토링