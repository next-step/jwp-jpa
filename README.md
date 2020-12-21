# jpa

---

## 1단계 - 엔티티 매핑
### 요구 사항
- [X] DDL을 보고 엔티티 클래스 작성
- [X] 리포지토리 클래스 작성
- [X] @DataJpaTest를 사용한 학습 테스트 작성

## 2단계 - 연관 관계 매핑
### 요구 사항
- [X] 지하철역과 노선이 있다.
- [X] 지하철역은 여러 노선에 소속될 수 있다.
    - [X] 환승역을 고려한다.
- [X] 노선 조회 시 속한 지하철역을 볼 수 있다.
- [X] 지하철역 조회 시 어느 노선에 속한지 볼 수 있다.
- [X] 사용자와 즐겨찾기가 있다.
- [X] 사용자는 여러 즐겨찾기를 가질 수 있다.
- [X] 즐겨찾기에는 출발역과 도착역이 포함되어 있다.
