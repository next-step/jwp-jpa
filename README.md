# jpa
## Step1 - 엔티티 매핑
### 요구사항
line, station, member, favorite 엔티티 작성 및 학습테스트 작성하기 

## Step2 - 연관 관계 매핑
### 요구사항
- 지하철역과 노선이 있다 
- 지하철역은 여러 노선에 소속될 수 있다
    - 환승역을 고려한다
- 노선 조회 시 속한 지하역을 볼 수 있다
- 지하철역 조회 시 어느 노선에 속한지 볼 수 있다
    - Station station = findByName("사당역")
    - station.getLine() -> 4호선
- 사용자와 즐겨찾기가 있다 (Member, Favorite)
- 사용자는 여러 즐겨찾기를 가진다
    - Member(1) : Favorite(N)
- 즐겨찾기에는 출발역과 도착역이 있다
